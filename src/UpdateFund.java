import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class UpdateFund extends VoltProcedure {


	private static final SQLStmt UPDATE_FUND = new SQLStmt(
			"update funds set fund_limit = ? where fund_id = ?");
	
	public VoltTable[] run(int loanId, int fundId, double loanAmt) {
		voltQueueSQL(UPDATE_FUND, loanAmt, fundId);
		return voltExecuteSQL();
	}
}
