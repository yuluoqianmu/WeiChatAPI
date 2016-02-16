package com.lyz.api.bean;

import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
import com.lyz.validate.ValidateStringIn;

/**
 * 支付宝支付回调接口请求参数
 * 写入支付记录（定金、全款）
 * @author luyongzhao
 *
 */
public class AliPayReq extends BaseReq {

	/**覆盖父类属性，不做判断处理*/
	private String key = "alipay";
	private String ver = "1.0";
	private String id = "id";
	/*mac地址去除中间分隔符经过md5加密32位大写*/
	private String pyId = "pid";
	/*ios的id*/
	private String openudid = "openid";
	/*用户登录id*/
	private String uid = "uid";
	private String pt = "iphone";
	/*md5校验值，32位大写*/
	private String r = "28260C1909A306979BBA5F56F31742E9";
	private long t = System.currentTimeMillis();
	
	
	
	/*通知的发送时间。 格式为 yyyy-MM-dd HH:mm:ss*/
//	@ValidateNotNull
	private String notify_time;
	/*通知的类型:trade_status_sync */
//	@ValidateNotNull
	private String notify_type;
	/*通知校验的id*/
//	@ValidateNotNull
	private String notify_id;
	/* 签名方式 ,固定取值为 RSA*/
//	@ValidateNotNull
//	@ValidateStringIn(value="RSA")
	private String sign_type;
	/*签名：1glihU9DPWee+UJ82u 3+mw3Bdnr9u01at0M/x JnPsGuHh+JA5bk3zb WaoWhU6GmLab3dIM 4JNdktTcEUI9/FBGhgf LO39BKX/eBCFQ3bXA mIZn4l26fiwoO613BptT 44GTEtnPiQ6+tnLsGlV SrFZaLB9FVhrGfipH2S WJcnwYs= */
//	@ValidateNotNull
	private String sign;
	/*商户网站唯 一订单号 */
//	@ValidateNotNull
	private String out_trade_no;
	/*商品名称*/
	private String subject;
	/*支付类型*/
	private String payment_type;
	/*支付宝交易 号 */
	private String trade_no;
	/*交易状态 */
	private String trade_status; 
	/*卖家支付宝 用户号 */
	private String seller_id;
	/*卖家支付宝 账号 */
	private String seller_email;
	/*买家支付宝 用户号 */
	private String buyer_id;
	/*买家支付宝 账号 */
	private String buyer_email;
	/*交易金额 */
	private double total_fee;
	/*购买数量，固定取值为 1（请求 时使用的是 total_fee）*/
	private int quantity;
	/*商品单价:price 等于 total_fee（请求时使 用的是 total_fee）。 */
	private double price;
	/*商品描述 */
	private String body;
	/*该笔交易创建的时间。 格式为 yyyy-MM-dd HH:mm:ss。 */
	private String gmt_create;
	/*该笔交易的买家付款时间。 格式为 yyyy-MM-dd HH:mm:ss。 */
	private String gmt_payment;
	/*是否调整总 价:N*/
	private String is_total_fee_adjust;
	/*是否使用红 包买家*/
	private String use_coupon;
	/*折扣*/
	private String discount;
	/*退款状态*/
	private String refund_status;
	/*退款时间*/
	private String gmt_refund ;
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	public double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public String getGmt_refund() {
		return gmt_refund;
	}
	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPyId() {
		return pyId;
	}
	public void setPyId(String pyId) {
		this.pyId = pyId;
	}
	public String getOpenudid() {
		return openudid;
	}
	public void setOpenudid(String openudid) {
		this.openudid = openudid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPt() {
		return pt;
	}
	public void setPt(String pt) {
		this.pt = pt;
	}
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r = r;
	}
	public long getT() {
		return t;
	}
	public void setT(long t) {
		this.t = t;
	}
	
	
	
	
	
	
}
