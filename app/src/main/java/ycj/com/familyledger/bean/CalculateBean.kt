package ycj.com.familyledger.bean

/**
 * @author: ycj
 * @date: 2017-06-24 18:54
 * @version V1.0 <expendMoney 个人花费--支出的金额，valueMoney 差值大于零表示收入，小于零表示还需要支出 >
 */
data class CalculateBean(var userName: String, var average: String, var totalMoney: String, var phoneNumber: String, var expendMoney: String, var valueMoney: String)