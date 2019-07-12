package ro.victor.unittest.bank.repo;

import org.apache.ibatis.annotations.Mapper;
import ro.victor.unittest.bank.vo.ClientSearchCriteria;
import ro.victor.unittest.bank.vo.ClientSearchResult;

import java.util.List;

@Mapper
public interface ClientMybatisRepository {
    List<ClientSearchResult> search(ClientSearchCriteria criteria);
}
