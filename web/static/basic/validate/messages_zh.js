/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
$.extend( $.validator.messages, {
	required: "不允许为空",
	remote: "数据输入错误，请重新输入",
	email: "请输入正确的E-MAIL地址",
	url: "请输入合法的网址",
	date: "请输入合法的日期",
	dateISO: "请输入合法的日期 (YYYY-MM-DD)",
	number: "请输入合法的数字",
	digits: "请输入整数",
	creditcard: "请输入合法的信用卡号码",
	equalTo: "两次输入的输入不相同",
	accept:"文件后缀不符合要求",
	extension: "该文件不允许使用",
	maxlength: $.validator.format( "输入内容长度不能大于 {0}" ),
	minlength: $.validator.format( "输入内容长度不能少于 {0}" ),
	rangelength: $.validator.format( "输入数据的长度应为{0} ~ {1} " ),
	range: $.validator.format( "请输入一个介于 {0} 到 {1} 之间的数据" ),
	max: $.validator.format( "可以输入的最大值为 {0} " ),
	min: $.validator.format( "可以输入的最小值为 {0} " )
} );
