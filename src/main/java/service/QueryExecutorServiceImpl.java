package service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import repository.QueryExecutorRepository;

@Service
public class QueryExecutorServiceImpl implements QueryExecutorService {
	@Resource
	private QueryExecutorRepository queryExecutorRepository;

	@Override
	public void executeQuery1() {
		queryExecutorRepository.executeQuery1();

	}

	@Override
	public void executeQuery2() {
		queryExecutorRepository.executeQuery2();
	}
	

}
