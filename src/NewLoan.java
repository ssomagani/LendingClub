import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class NewLoan extends VoltProcedure {

	private static final SQLStmt INSERT = new SQLStmt("INSERT INTO NEW_LOANS VALUES("

			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
			+ ")");
	private static final SQLStmt INSERT_STREAM = new SQLStmt("INSERT INTO LOAN_BY_RISK values (?, ?, ?)");
	
	Model model = new Model();
	
	public VoltTable[] run(int loan_id, 
			String arg1, String arg2, String arg3, String arg4, String arg5, 
			String arg6, String arg7, String arg8, String arg9, String arg10, 
			String arg11, String arg12, String arg13, String arg14, String arg15, 
			String arg16, String arg17, String arg18, String arg19, String arg20, 
			String arg21, String arg22, String arg23, String arg24, String arg25, 
			String arg26, String arg27, String arg28, String arg29, String arg30, 
			String arg31, String arg32, String arg33, String arg34, String arg35, 
			String arg36, String arg37, String arg38, String arg39, String arg40, 
			String arg41, String arg42, String arg43, String arg44, String arg45, 
			String arg46, String arg47, String arg48, String arg49, String arg50, 
			String arg51, String arg52, String arg53, String arg54, String arg55, 
			String arg56, String arg57, String arg58, String arg59, String arg60, 
			String arg61, String arg62, String arg63, String arg64, String arg65, 
			String arg66, String arg67, String arg68, String arg69, String arg70, 
			String arg71, String arg72, String arg73, String arg74, String arg75, 
			String arg76, String arg77, String arg78, String arg79, String arg80, 
			String arg81, String arg82, String arg83, String arg84, String arg85, 
			String arg86, String arg87, String arg88, String arg89, String arg90, 
			String arg91, String arg92, String arg93, String arg94, String arg95, 
			String arg96, String arg97, String arg98, String arg99, String arg100, 
			String arg101, String arg102, String arg103, String arg104, String arg105, 
			String arg106, String arg107, String arg108, String arg109, String arg110, 
			String arg111, String arg112, String arg113, String arg114, String arg115, 
			String arg116, String arg117, String arg118, String arg119, String arg120, 
			String arg121, String arg122, String arg123, String arg124, String arg125, 
			String arg126, String arg127, String arg128, String arg129, String arg130, 
			String arg131, String arg132, String arg133, String arg134, String arg135, 
			String arg136, String arg137, String arg138, String arg139, String arg140, 
			String arg141, String arg142, String arg143, String arg144, String arg145, 
			String arg146, String arg147, String arg148, String arg149, String arg150
			) {

		Object[] args = {loan_id, 
				arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, 
				arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, 
				arg20, arg21, arg22, arg23, arg24, arg25, arg26, arg27, arg28, arg29, 
				arg30, arg31, arg32, arg33, arg34, arg35, arg36, arg37, arg38, arg39, 
				arg40, arg41, arg42, arg43, arg44, arg45, arg46, arg47, arg48, arg49, 
				arg50, arg51, arg52, arg53, arg54, arg55, arg56, arg57, arg58, arg59, 
				arg60, arg61, arg62, arg63, arg64, arg65, arg66, arg67, arg68, arg69, 
				arg70, arg71, arg72, arg73, arg74, arg75, arg76, arg77, arg78, arg79, 
				arg80, arg81, arg82, arg83, arg84, arg85, arg86, arg87, arg88, arg89, 
				arg90, arg91, arg92, arg93, arg94, arg95, arg96, arg97, arg98, arg99, 
				arg100, arg101, arg102, arg103, arg104, arg105, arg106, arg107, arg108, arg109, 
				arg110, arg111, arg112, arg113, arg114, arg115, arg116, arg117, arg118, arg119, 
				arg120, arg121, arg122, arg123, arg124, arg125, arg126, arg127, arg128, arg129, 
				arg130, arg131, arg132, arg133, arg134, arg135, arg136, arg137, arg138, arg139, 
				arg140, arg141, arg142, arg143, arg144, arg145, arg146, arg147, arg148, arg149, arg150};
		
		double risk = model.makePrediction(args);
		double loanAmt = Double.parseDouble(arg2);
		
		int risk_int = Math.abs((int) risk % 8) + 1;
		arg1 = Integer.toString(risk_int);
		
		voltQueueSQL(INSERT,
				loan_id, 
				arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, 
				arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, 
				arg20, arg21, arg22, arg23, arg24, arg25, arg26, arg27, arg28, arg29, 
				arg30, arg31, arg32, arg33, arg34, arg35, arg36, arg37, arg38, arg39, 
				arg40, arg41, arg42, arg43, arg44, arg45, arg46, arg47, arg48, arg49, 
				arg50, arg51, arg52, arg53, arg54, arg55, arg56, arg57, arg58, arg59, 
				arg60, arg61, arg62, arg63, arg64, arg65, arg66, arg67, arg68, arg69, 
				arg70, arg71, arg72, arg73, arg74, arg75, arg76, arg77, arg78, arg79, 
				arg80, arg81, arg82, arg83, arg84, arg85, arg86, arg87, arg88, arg89, 
				arg90, arg91, arg92, arg93, arg94, arg95, arg96, arg97, arg98, arg99, 
				arg100, arg101, arg102, arg103, arg104, arg105, arg106, arg107, arg108, arg109, 
				arg110, arg111, arg112, arg113, arg114, arg115, arg116, arg117, arg118, arg119, 
				arg120, arg121, arg122, arg123, arg124, arg125, arg126, arg127, arg128, arg129, 
				arg130, arg131, arg132, arg133, arg134, arg135, arg136, arg137, arg138, arg139, 
				arg140, arg141, arg142, arg143, arg144, arg145, arg146, arg147, arg148, arg149, arg150
				);
		voltQueueSQL(INSERT_STREAM, risk, loan_id, loanAmt);
		
		return voltExecuteSQL(true);
	}
}
