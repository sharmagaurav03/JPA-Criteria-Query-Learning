package repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import model.Address;
import model.Customer;
import model.CustomerDetails;
import model.Customer_;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class QueryExecutorRepositoryImpl implements QueryExecutorRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void executeQuery1() {
		TypedQuery<Customer> query = entityManager.createQuery(
				"SELECT c FROM Customer c", Customer.class);
		List<Customer> results = query.getResultList();
		for (Customer customer : results)
			printObject(customer);

	}

	@Override
	public void executeQuery3() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		Predicate condition = criteriaBuilder.equal(
				customerRoot.get(Customer_.firstname), "Boyd");
		criteriaQuery.where(condition);

		TypedQuery<Customer> typedQuery = entityManager
				.createQuery(criteriaQuery);
		List<Customer> result = typedQuery.getResultList();
		printObject(result);

	}

	@Override
	public void executeQuery4() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder
				.createQuery(Double.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);

		criteriaQuery.select(criteriaBuilder.<Long> avg(customerRoot
				.get(Customer_.id)));

		TypedQuery<Double> typedQuery = entityManager
				.createQuery(criteriaQuery);
		Double result = typedQuery.getSingleResult();
		printObject(result);

	}

	@Override
	public void executeQuery5() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> Customer = criteriaQuery.from(Customer.class);
		Path<Long> balance = Customer.get(Customer_.id);
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.greaterThan(balance, 1L),
				criteriaBuilder.lessThan(balance, 10L)));
		TypedQuery<Customer> typedQuery = entityManager
				.createQuery(criteriaQuery);
		List<Customer> result = typedQuery.getResultList();
		printObject(result);
	}

	@Override
	public void executeQuery6() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> CustomerRoot = criteriaQuery.from(Customer.class);
		Path<String> name = CustomerRoot.get(Customer_.firstname);
		criteriaQuery.where(criteriaBuilder.in(name).value("Carter")
				.value("Dave").value("Boyd"));

		TypedQuery<Customer> typedQuery = entityManager
				.createQuery(criteriaQuery);
		List<Customer> result = typedQuery.getResultList();
		printObject(result);
	}

	@Override
	public void executeQuery7() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		SetJoin<Customer, Address> join = customerRoot
				.join(Customer_.addresses);
		TypedQuery<Customer> typedQuery = entityManager
				.createQuery(criteriaQuery);
		List<Customer> result = typedQuery.getResultList();
		printObject(result);
	}

	@Override
	public void executeQuery8() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> CustomerRoot = criteriaQuery.from(Customer.class);
		ParameterExpression<String> nameExpression = criteriaBuilder
				.parameter(String.class);
		Predicate condition = criteriaBuilder.equal(
				CustomerRoot.get(Customer_.firstname), nameExpression);
		criteriaQuery.where(condition);

		TypedQuery<Customer> typedQuery = entityManager.createQuery(
				criteriaQuery).setParameter(nameExpression, "Dave");
		List<Customer> result = typedQuery.getResultList();
		printObject(result);
	}

	@Override
	public void executeQuery9() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerDetails> criteriaQuery = criteriaBuilder
				.createQuery(CustomerDetails.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		ParameterExpression<String> nameExpression = criteriaBuilder
				.parameter(String.class);
		Predicate condition = criteriaBuilder.equal(
				customerRoot.get(Customer_.firstname), nameExpression);
		criteriaQuery.where(condition);

		criteriaQuery.select(criteriaBuilder.construct(CustomerDetails.class,
				customerRoot.get(Customer_.firstname),
				customerRoot.get(Customer_.id)));

		TypedQuery<CustomerDetails> typedQuery = entityManager.createQuery(
				criteriaQuery).setParameter(nameExpression, "Boyd");
		List<CustomerDetails> result = typedQuery.getResultList();
		printObject(result);
	}

	@Override
	public void executeQuery10() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerDetails> criteriaQuery = criteriaBuilder
				.createQuery(CustomerDetails.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		ParameterExpression<String> nameExpression = criteriaBuilder
				.parameter(String.class);
		Predicate condition = criteriaBuilder.equal(
				customerRoot.get(Customer_.firstname), nameExpression);
		criteriaQuery.where(condition);

		criteriaQuery.multiselect(customerRoot.get(Customer_.firstname),
				customerRoot.get(Customer_.id));

		TypedQuery<CustomerDetails> typedQuery = entityManager.createQuery(
				criteriaQuery).setParameter(nameExpression, "Boyd");
		List<CustomerDetails> result = typedQuery.getResultList();
		printObject(result);
	}

	@Override
	public void executeQuery11() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		criteriaQuery.multiselect(customerRoot.get(Customer_.firstname),
				customerRoot.get(Customer_.lastname));

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Tuple> result = typedQuery.getResultList();
		printObject(result);// it is giving error while printing.
	}
	
	@Override
	public void executeQuery12() {
		Class<?> cls;
		try {
			cls = (Class<?>) Class.forName("model.Customer");

			Metamodel model = entityManager.getMetamodel();
			EntityType<?> entity = model.entity(cls);

			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();
			CriteriaQuery<?> criteriaQuery = criteriaBuilder
					.createQuery(cls);
			Root<?> customerRoot = criteriaQuery.from(entity);
			Path<Integer> id = customerRoot.<Integer> get("id");
			criteriaQuery.where(criteriaBuilder.and(
					criteriaBuilder.greaterThan(id, 1),
					criteriaBuilder.lessThan(id, 20)));

			TypedQuery<?> typedQuery = entityManager
					.createQuery(criteriaQuery);
			List<?> result = typedQuery.getResultList();
			printObject(result);// it is giving error while printing.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}


	@Override
	public void executeQuery13() {
		System.out.println("Before updating the order..................");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		
		
		criteriaQuery.orderBy(criteriaBuilder.asc(customerRoot.get(Customer_.firstname)));
		List<Customer> result = entityManager.createQuery(criteriaQuery).getResultList();
		printObject(result);
		
		System.out.println("After updating the order..................");
		
		// start editing
		List<Order> orders = criteriaQuery.getOrderList();
		List<Order> newOrders = new ArrayList<Order>(orders);
		newOrders.add(criteriaBuilder.desc(customerRoot.get(Customer_.id)));
		criteriaQuery.orderBy(newOrders);
		List<Customer> result2 = entityManager.createQuery(criteriaQuery).getResultList();
		printObject(result2);
		
	}
	
	@Override
	public void executeQuery2() {
		String columnName1= "firstname";
		String cond1="startswith";
		String value1="Boyd";
		String columnName2= "id";
		String cond2="lt";
		String value2="5";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder
				.createQuery(Customer.class);
		Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
		
		
		Predicate condition = criteriaBuilder.equal(
				customerRoot.get(Customer_.firstname), "Boyd");
		
		Predicate condition1 = criteriaBuilder.gt(
				customerRoot.get(Customer_.id), 2);
		
		Predicate condition2 = criteriaBuilder.lt(
				customerRoot.get(Customer_.id), 5);
		
/*		ArrayList<Predicate> list=new ArrayList<Predicate>();
		list.add(condition);list.add(condition1);list.add(condition2);*/
		
		criteriaQuery.where(condition, condition1, condition2);
		
		
		TypedQuery<Customer> typedQuery = entityManager
				.createQuery(criteriaQuery);
		List<Customer> result = typedQuery.getResultList();
		printObject(result);
		
	}

	private void printObject(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(obj));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
