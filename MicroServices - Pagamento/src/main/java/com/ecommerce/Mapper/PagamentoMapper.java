package com.ecommerce.Mapper;

import com.ecommerce.Model.Pagamento;
import com.ecommerce.VO.v1.PagamentoVO;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    PagamentoVO pagamentoToVo(Pagamento pagamento);

    Pagamento pagamentoVOToPagamentoVO(PagamentoVO pagamento);
}
