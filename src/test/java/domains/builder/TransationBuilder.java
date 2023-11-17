package domains.builder;

import java.time.LocalDateTime;

import com.junit.domain.Account;
import com.junit.domain.Transation;

public final class TransationBuilder {

	private Transation transation;

	private TransationBuilder() {
	}

	public static TransationBuilder oneTransation() {
		TransationBuilder builder = new TransationBuilder();
		initData(builder);
		return builder;
	}

	public static void initData(TransationBuilder builder) {
		builder.transation = new Transation();
		Transation element = builder.transation;

		element.setId(1L);
		element.setAccount(AccountBuilder.getInstanceAccount().now());
		element.setDate(LocalDateTime.now());
		element.setStatus(false);
		element.setDescription("Default transation");
		element.setValue(0D);
	}

	public TransationBuilder withID(Long id) {
		transation.setId(id);
		return this;
	}

	public TransationBuilder withDescription(String desc) {
		transation.setDescription(desc);
		return this;
	}

	public TransationBuilder withValue(Double vl) {
		transation.setValue(vl);
		return this;
	}

	public TransationBuilder withDate(LocalDateTime a) {
		transation.setDate(a);
		return this;
	}

	public TransationBuilder withStatus(Boolean a) {
		transation.setStatus(a);
		return this;
	}

	public TransationBuilder withAccount(Account a) {
		transation.setAccount(a);
		return this;
	}

	public Transation now() {
		return transation;
	}
}
