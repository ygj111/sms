/**
 * French translation for bootstrap-datetimepicker
 * Nico Mollet <nico.mollet@gmail.com>
 */
;(function($){
	$.fn.datetimepicker.dates['fr'] = {
		days: ["Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"],
		daysShort: ["Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"],
		daysMin: ["D", "L", "Ma", "Me", "J", "V", "S", "D"],
		months: ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"],
		monthsShort: ["Jan", "Fev", "Mar", "Avr", "Mai", "Jui", "Jul", "Aou", "Sep", "Oct", "Nov", "Dec"],
		today: "Aujourd'hui",
		suffix: [],
		meridiem: ["am", "pm"],
		weekStart: 1,
		format: "dd/mm/yyyy hh:ii"
	};
}(jQuery));

;(function($){
	 $.fn.datetimepicker.dates['zh-CN'] = {
	   days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
	   daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
	   daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
	   months: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
	   monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	   today: "今日",
	   suffix: [],
	   meridiem: ["上午", "下午"],
	   format: "yyyy-mm-dd hh:ii:ss" /*控制显示格式,默认为空，显示小时分钟*/
	 };
}(jQuery));
