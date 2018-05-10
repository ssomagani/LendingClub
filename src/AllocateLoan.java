import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class AllocateLoan extends VoltProcedure {

	private static final SQLStmt GET_FUNDS = new SQLStmt(
			"select fund_id from ("
			+ "select a.fund_id, a.risk, a.alloc_amt - coalesce(b.total_inv, 0) as fund_rem "
			+ "from FUNDS_RISK_DISTRIB a "
			+ "left join "
			+ "FUNDS_INV_RISK b "
			+ "on "
			+ "a.fund_id = b.fund_id and "
			+ "a.risk = b.risk "
			+ "where a.risk = ? "
			+ "group by a.fund_id, a.risk, a.alloc_amt - coalesce(b.total_inv, 0) ) "
			+ "X where X.FUND_REM > ? order by x.fund_rem desc");
	
	private static final SQLStmt OVERFLOW_LOAN = new SQLStmt("INSERT INTO overflow_loan_stream values (?)");
	private static final SQLStmt INVEST_LOAN = new SQLStmt("insert into inv_loans ( loan_id, fund_id, risk, inv_amount) values (?, ?, ?, ?)");

	public VoltTable[] run(int risk, int loanId, double loanAmt) {
		
		Integer fundId = pickAFund(risk, loanId, loanAmt);
		
		if(fundId == null) {
			voltQueueSQL(OVERFLOW_LOAN, loanId);
		} else {
			voltQueueSQL(INVEST_LOAN, loanId, fundId, risk, loanAmt);
		}
		
		return voltExecuteSQL(true);
	}
	
	private Integer pickAFund(int risk, int loan_id, double loanAmt) {
		voltQueueSQL(GET_FUNDS, risk, loanAmt);
		VoltTable funds = voltExecuteSQL()[0];
		if(funds.advanceRow())
			return (int) funds.getLong("fund_id");
		return null;
	}
}
