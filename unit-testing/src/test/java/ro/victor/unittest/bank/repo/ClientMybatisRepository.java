package ro.victor.unittest.bank.repo;

import ro.victor.unittest.bank.vo.ClientSearchCriteria;
import ro.victor.unittest.bank.vo.ClientSearchResult;

import java.util.List;

public interface ClientMybatisRepository {
    List<ClientSearchResult> search(ClientSearchCriteria criteria);
}
