package namedqueries;

public enum NamedQuery {
	query1(
			"Select c.firstname, c.lastname, p.name from Customer c inner join Orders o on o.customer_id=c.id inner join LineItem li on li.order_id=o.id inner join Product p on p.id=li.product_id where o.id= 1 and c.firstname='Dave'",
			true), query2(
			"Select c.firstname, c.lastname, p.name from Customer c inner join Orders o on o.customer_id=c.id inner join LineItem li on li.order_id=o.id inner join Product p on p.id=li.product_id where o.id= :id",
			true), query3(
			"Select o.id, c.firstname, c.lastname From Order o JOIN o.customer c where o.id=1",
			false), query4(
			"Select o.id, c.firstname, c.lastname From Order o JOIN o.customer c where o.id= :id",
			false), query5(
			"Select c.firstname, c.lastname, p.name from Customer c inner join Orders o on o.customer_id=c.id inner join LineItem li on li.order_id=o.id inner join Product p on p.id=li.product_id where o.id= :id and c.firstname=:firstname",
			true), query6(
					"Select c.firstname, c.lastname from Customer c where c.firstname=:firstname",
					true)

	;
	private String queryString;
	private boolean isNative;

	NamedQuery(String queryString, boolean isNative) {
		this.queryString = queryString;
		this.isNative = isNative;
	}

	public String getQueryString() {
		return this.queryString;
	}

	public boolean isNative() {
		return this.isNative;
	}
}
