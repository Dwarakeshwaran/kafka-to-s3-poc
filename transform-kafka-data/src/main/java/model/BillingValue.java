package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillingValue {

	private String id;
	private String alternate_id;
	private String invoice_code;
	private String gross_contract;
	private String pymts_received;
	private String pymts_received_d;
	private String contract_pymt;
	private String variable_pymt_code;

}
