#!/bin/bash

echo "🔁 Waiting for Kafka Connect to start..."

# Waiting for Kafka Connect REST API
until curl -s http://kafka-connect:8083/; do
  echo "⏳ Kafka Connect not ready, please wait..."
  sleep 5
done

echo "🚀 Creating Debezium connector 'sale-connector'..."

# send JSON create connector
curl -i -X POST \
  -H "Accept:application/json" \
  -H "Content-Type:application/json" \
  --data-binary "@/kafka/connectors/sale-connector.json" \
  http://kafka-connect:8083/connectors/

echo "✅ Created successfully connector."
