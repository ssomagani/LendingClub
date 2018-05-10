import java.text.ParseException;
import java.text.SimpleDateFormat;

import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.RegressionModelPrediction;

public class Model {

	SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");

	EasyPredictModelWrapper model;
	
	int count = 0;

	public Model() {
		hex.genmodel.GenModel rawModel;
		try {
			rawModel = (hex.genmodel.GenModel) Class.forName("gbm_ceac47e1_64f2_4bb9_b83e_cf16a30c0a17").newInstance();

			model = new EasyPredictModelWrapper(
					new EasyPredictModelWrapper.Config()
					.setModel(rawModel)
					.setConvertInvalidNumbersToNa(true)
					.setConvertUnknownCategoricalLevelsToNa(true));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public double makePrediction(Object[] args) {
		
		double predctnValue = 0.0;
		
		RowData row = new RowData();
		row.put("id", args[0]);
		row.put("member_id", args[1]);
		row.put("loan_amnt", args[2]);
		row.put("funded_amnt", args[3]);
		row.put("funded_amnt_inv", args[4]);
		row.put("term", args[5]);
		row.put("int_rate", args[6]);
		row.put("installment", args[7]);
		row.put("grade", args[8]);
		row.put("sub_grade", args[9]);
		row.put("emp_title", args[10]);
		row.put("emp_length", args[11]);
		row.put("home_ownership", args[12]);
		row.put("annual_inc", args[13]);
		row.put("verification_status", args[14]);
		row.put("issue_d", args[15]);
		row.put("loan_status", args[16]);
		row.put("pymnt_plan", args[17]);
		row.put("url", args[18]);
		row.put("desc", args[19]);
		row.put("purpose", args[20]);
		row.put("title", args[21]);
		row.put("zip_code", args[22]);
		row.put("addr_state", args[23]);
		row.put("dti", args[24]);
		row.put("delinq_2yrs", args[25]);

		String earlistCrLine = (String) args[26];
		double earlistCrLineTime = 0;
		try {
			earlistCrLineTime = sdf.parse(earlistCrLine).getTime();
		} catch (ParseException e2) {		}

		row.put("earliest_cr_line", earlistCrLineTime);

		row.put("fico_range_low", args[27]);
		row.put("fico_range_high", args[28]);
		row.put("inq_last_6mths", args[29]);
		row.put("mths_since_last_delinq", args[30]);
		row.put("mths_since_last_record", args[31]);
		row.put("open_acc", args[32]);
		row.put("pub_rec", args[33]);
		row.put("revol_bal", args[34]);
		row.put("revol_util", args[35]);
		row.put("total_acc", args[36]);
		row.put("initial_list_status", args[37]);
		row.put("out_prncp", args[38]);
		row.put("out_prncp_inv", args[39]);
		row.put("total_pymnt", args[40]);
		row.put("total_pymnt_inv", args[41]);
		row.put("total_rec_prncp", args[42]);
		row.put("total_rec_int", args[43]);
		row.put("total_rec_late_fee", args[44]);
		row.put("recoveries", args[45]);
		row.put("collection_recovery_fee", args[46]);
		row.put("last_pymnt_d", args[47]);
		row.put("last_pymnt_amnt", args[48]);
		row.put("next_pymnt_d", args[49]);
		row.put("last_credit_pull_d", args[50]);
		row.put("last_fico_range_high", args[51]);
		row.put("last_fico_range_low", args[52]);
		row.put("collections_12_mths_ex_med", args[53]);
		row.put("mths_since_last_major_derog", args[54]);
		row.put("policy_code", args[55]);
		row.put("application_type", args[56]);
		row.put("annual_inc_joint", args[57]);
		row.put("dti_joint", args[58]);
		row.put("verification_status_joint", args[59]);
		row.put("acc_now_delinq", args[60]);
		row.put("tot_coll_amt", args[61]);
		row.put("tot_cur_bal", args[62]);
		row.put("open_acc_6m", args[63]);
		row.put("open_act_il", args[64]);
		row.put("open_il_12m", args[65]);
		row.put("open_il_24m", args[66]);

		Double mths_since_rcnt_il = 0d;
		try {
			mths_since_rcnt_il = (Double) (args[67]);
		} catch (Exception e){}
		row.put("mths_since_rcnt_il", mths_since_rcnt_il);


		Double total_bal_il = 0d;
		try {
			total_bal_il = (Double) (args[68]);
		} catch (Exception e){}
		row.put("total_bal_il", total_bal_il);


		Double il_util = 0d;
		try {
			il_util = (Double) (args[69]);
		} catch (Exception e){}
		row.put("il_util", il_util);

		row.put("open_rv_12m", args[70]);
		row.put("open_rv_24m", args[71]);
		row.put("max_bal_bc", args[72]);
		row.put("all_util", args[73]);
		row.put("total_rev_hi_lim", args[74]);
		row.put("inq_fi", args[75]);
		row.put("total_cu_tl", args[76]);
		row.put("inq_last_12m", args[77]);
		row.put("acc_open_past_24mths", args[78]);
		row.put("avg_cur_bal", args[79]);
		row.put("bc_open_to_buy", args[80]);
		row.put("bc_util", args[81]);
		row.put("chargeoff_within_12_mths", args[82]);
		row.put("delinq_amnt", args[83]);

		Double mo_sin_old_il_acct = 0d;
		try {
			mo_sin_old_il_acct = (Double) (args[84]);
		} catch (Exception e){}

		row.put("mo_sin_old_il_acct", mo_sin_old_il_acct);

		row.put("mo_sin_old_rev_tl_op", args[85]);
		row.put("mo_sin_rcnt_rev_tl_op", args[86]);
		row.put("mo_sin_rcnt_tl", args[87]);
		row.put("mort_acc", args[88]);
		row.put("mths_since_recent_bc", args[89]);
		row.put("mths_since_recent_bc_dlq", args[90]);

		String mths_since_recent_inq = (String) args[91];
		double mths_since_recent_inqTime = 0;

		try {
			mths_since_recent_inqTime = sdf.parse(mths_since_recent_inq).getTime();
		} catch(ParseException e) {}

		row.put("mths_since_recent_inq", mths_since_recent_inqTime);

		row.put("mths_since_recent_revol_delinq", args[92]);
		row.put("num_accts_ever_120_pd", args[93]);
		row.put("num_actv_bc_tl", args[94]);
		row.put("num_actv_rev_tl", args[95]);
		row.put("num_bc_sats", args[96]);
		row.put("num_bc_tl", args[97]);
		row.put("num_il_tl", args[98]);
		row.put("num_op_rev_tl", args[99]);
		row.put("num_rev_accts", args[100]);
		row.put("num_rev_tl_bal_gt_0", args[101]);
		row.put("num_sats", args[102]);

		String num_tl_120dpd_2m = (String) args[103];
		double num_tl_120dpd_2mTime = 0;

		try {
			num_tl_120dpd_2mTime = Double.parseDouble(num_tl_120dpd_2m);
		} catch (Exception e){}

		row.put("num_tl_120dpd_2m", num_tl_120dpd_2mTime);

		row.put("num_tl_30dpd", args[104]);
		row.put("num_tl_90g_dpd_24m", args[105]);
		row.put("num_tl_op_past_12m", args[106]);
		row.put("pct_tl_nvr_dlq", args[107]);
		row.put("percent_bc_gt_75", args[108]);
		row.put("pub_rec_bankruptcies", args[109]);
		row.put("tax_liens", args[110]);
		row.put("tot_hi_cred_lim", args[111]);
		row.put("total_bal_ex_mort", args[112]);
		row.put("total_bc_limit", args[113]);
		row.put("total_il_high_credit_limit", args[114]);
		row.put("revol_bal_joint", args[115]);
		row.put("sec_app_fico_range_low", args[116]);
		row.put("sec_app_fico_range_high", args[117]);

		String secAppEarliestCrLine = (String) args[118];
		double secAppEarliestCrLineTime = 0;
		try {
			secAppEarliestCrLineTime = sdf.parse(secAppEarliestCrLine).getTime();
		} catch (ParseException e1) {		}

		row.put("sec_app_earliest_cr_line", secAppEarliestCrLineTime);

		row.put("sec_app_inq_last_6mths", args[119]);
		row.put("sec_app_mort_acc", args[120]);
		row.put("sec_app_open_acc", args[121]);
		row.put("sec_app_revol_util", args[122]);
		row.put("sec_app_open_act_il", args[123]);
		row.put("sec_app_num_rev_accts", args[124]);
		row.put("sec_app_chargeoff_within_12_mths", args[125]);
		row.put("sec_app_collections_12_mths_ex_med", args[126]);
		row.put("sec_app_mths_since_last_major_derog", args[127]);
		row.put("hardship_flag", args[128]);
		row.put("hardship_type", args[129]);
		//        row.put("hardship_reason", args[130]);
		row.put("hardship_status", args[131]);
		row.put("deferral_term", args[132]);
		row.put("hardship_amount", args[133]);

		String hardship_start_date = (String) args[134];
		double hardship_start_dateTime = 0;

		try {
			hardship_start_dateTime = sdf.parse(hardship_start_date).getTime();
		} catch (ParseException e) {}

		row.put("hardship_start_date", hardship_start_dateTime);

		String hardship_end_date = (String) args[135];
		double hardship_end_dateTime = 0;

		try {
			hardship_end_dateTime = sdf.parse(hardship_end_date).getTime();
		} catch (ParseException e) {}

		row.put("hardship_end_date", hardship_end_dateTime);

		String payment_plan_start_date = (String) args[136];
		double payment_plan_start_dateTime = 0;

		try {
			payment_plan_start_dateTime = sdf.parse(payment_plan_start_date).getTime();
		} catch (ParseException e){} 

		row.put("payment_plan_start_date", payment_plan_start_dateTime);

		row.put("hardship_length", args[137]);
		row.put("hardship_dpd", args[138]);
		row.put("hardship_loan_status", args[139]);
		row.put("orig_projected_additional_accrued_interest", args[140]);
		//        row.put("hardship_payoff_balance_amount", args[141]);
		row.put("hardship_last_payment_amount", args[142]);
		row.put("disbursement_method", args[143]);
		row.put("debt_settlement_flag", args[144]);

		String debt_settlement_flag_date = (String) args[145];
		double debt_settlement_flag_dateTime = 0;

		try {
			debt_settlement_flag_dateTime = sdf.parse(debt_settlement_flag_date).getTime();
		} catch (ParseException e1) {		}

		row.put("debt_settlement_flag_date", debt_settlement_flag_dateTime);

		row.put("settlement_status", args[146]);

		String settlement_date = (String) args[147];
		double settlement_dateTime = 0;
		try {
			settlement_dateTime = sdf.parse(settlement_date).getTime();
		} catch (ParseException e1) {		}

		row.put("SETTLEMENT_DATE", settlement_dateTime);

		row.put("settlement_amount", args[148]);
		row.put("settlement_percentage", args[149]);
		row.put("settlement_term", args[150]);

		RegressionModelPrediction p = null;
		try {
			p = model.predictRegression(row);
			if(p!= null)
				predctnValue = p.value;
		} catch (PredictException e) {
			e.printStackTrace();
		}

		return predctnValue;
	}
}
