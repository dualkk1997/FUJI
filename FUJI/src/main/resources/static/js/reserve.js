Vue.createApp({
  created: async function () {
    await this.loadCalendar()
    today = new Date()

    for (var i = 0; i < this.dateLimit; i++) {
      this.date.push({
        month: today.getMonth() + 1,
        day: today.getDate(),
        strval: (today.getMonth() + 1) + "/" + today.getDate()
      })
      today = new Date(today.setDate(today.getDate() + 1))
    }
    this.reserveForm = {
      interval: this.interval[0],
      name: "",
      phone: "",
      amount: 1,
      date: this.date[0].strval
    }


  },
  data() {
    return {

      interval: [
        "11:30", "12:30", "13:30", "17:30", "18:30", "19:30",
      ], // 有哪些時段可以訂位
      intervalLimit: 10, // 每個時段可以給幾個客人訂位
      date: [], // 可以訂位的日期
      dateLimit: 30, // 可以幾天以前提早訂位
      reserves: [

      ], //訂位資訊
      calendar: [

      ], // 顯示資訊
      reserveForm: {},

    };
  },
  methods: {
    sendReserve: async function () {
      var month = parseInt(this.reserveForm.date.substring(0, this.reserveForm.date.indexOf('/')))
      var day = parseInt(this.reserveForm.date.substring(this.reserveForm.date.indexOf('/') + 1))
      var calendar = this.calendar.find((element) => {
        return element.day == day && element.month == month
      })

      if(calendar.amount +  this.reserveForm.amount > 60) {
        Swal.fire({
          title: '預約失敗',
          html: "預約人數超過當日剩餘人數",
          icon: 'error',
          confirmButtonText: '確定'
        })
        return 
      }

      Swal.fire({
        title: '預約成功',
        html: this.reserveForm.name + "先生/小姐您好，您的預約時間為" + this.reserveForm.date + " " + this.reserveForm.interval + "。<br>人數為" + this.reserveForm.amount + " 人。<br>已預約成功，位子將保留十分鐘。",
        icon: 'success',
        confirmButtonText: '確定'
      })

      var data = {
        reservename: this.reserveForm.name,
        phone: this.reserveForm.phone,
        birthmonth: this.reserveForm.date.substring(0, this.reserveForm.date.indexOf('/')),
        birthdate: this.reserveForm.date.substring(this.reserveForm.date.indexOf('/') + 1),
        period: this.reserveForm.interval,
        total: this.reserveForm.amount
      }

      let res = await postData("http://localhost:8081/reserve/saveReserve", data)
      await this.loadCalendar()
    },
    showIntervalInfo: function (date) {
      Swal.fire({
        title: date.month + "月" + date.day +"日" ,
        html: "本日可訂位人數剩餘"+ ((60-date.amount)>0?(60-date.amount):0) + "人",
        icon: 'info',
        confirmButtonText: '確定'
      })
    },
    loadCalendar: async function () {
      var today = new Date()
      var start = new Date(today.setDate(today.getDate() - today.getDay()))
      this.reserves = await (await getData("http://localhost:8081/reserve/getReserve")).json()
      for (var i = 0; i < 35; i++) {
        this.calendar.push({
          month: start.getMonth() + 1,
          day: start.getDate(),
          amount: this.reserves.filter((reserve) => {
            return reserve.birthmonth == (start.getMonth() + 1) && reserve.birthdate == start.getDate()
          }).map(
            element => element.total
          ).reduce((x, y) => {
            return x + y
          }, 0),
          status: this.reserves.filter((reserve) => {
            return reserve.birthmonth == (start.getMonth() + 1) && reserve.birthdate == start.getDate()
          }).map(
            element => element.total
          ).reduce((x, y) => {
            return x + y
          }, 0) < 60
        })
        start = new Date(start.setDate(start.getDate() + 1))
      }
    }
  }
}).mount("#app");

function showIntervalModal() {
  $('#intervalModal').modal('show')
}