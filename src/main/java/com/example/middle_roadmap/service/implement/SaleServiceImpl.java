package com.example.middle_roadmap.service.implement;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.dto.sale.SaleId;
import com.example.middle_roadmap.entity.Sale;
import com.example.middle_roadmap.repository.SaleRepository;
import com.example.middle_roadmap.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.instancio.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

    @Override
    public BaseResponse uploadData() {
        List<Sale> sales = generateSales(730);
        saleRepository.saveAll(sales);
        return BaseResponse.simpleSuccess("success");
    }

    private List<Sale> generateSales(int count) {
        LocalDate start = LocalDate.of(2024, 1, 1);
        return IntStream.range(1, count + 1).mapToObj(i -> {
            SaleId saleId = new SaleId();
            saleId.setId((long) i+1000);
            saleId.setSaleDate(start.plusDays(i));

            return Instancio.of(Sale.class)
                    .set(Select.field(Sale::getSaleId), saleId)
                    .set(Select.field(Sale::getAmount), new BigInteger("100000" + i))
                    .create();
        }).collect(Collectors.toList());
    }
}
