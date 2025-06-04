#!/bin/sh

echo "🔁 Waiting for Kafka Connect to start..."

# Waiting for Kafka Connect REST API
until curl -s http://kafka-connect:8083/; do
  echo "⏳ Kafka Connect not ready, please wait..."
  sleep 5
done

echo "🚀 Creating Debezium connectors ..."

CONNECTOR_DIR="/kafka/connectors"

for connector_file in "$CONNECTOR_DIR"/*.json; do
  connector_name=$(basename "$connector_file" .json)
  echo "📤 Creating connector: $connector_name"

  response=$(curl -s -o /dev/null -w "%{http_code}" -X POST \
    -H "Accept:application/json" \
    -H "Content-Type:application/json" \
    --data-binary "@$connector_file" \
    http://kafka-connect:8083/connectors/)

  http_body=$(echo "$response" | sed '$d')
  http_status=$(echo "$response" | tail -n1)

  if [ "$response" -eq 201 ]; then
    echo "✅ Connector $connector_name created successfully."
  elif [ "$response" -eq 409 ]; then
    echo "⚠️  Connector $connector_name already exists. Skipping..."
  else
    echo "❌ Failed to create connector $connector_name (HTTP $http_status)"
    echo "🧾 Error response:"
    echo "$http_body"
  fi
done

