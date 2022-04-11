Vue.createApp({
  created: function() {
    var today = new Date()
    var start = new Date(today.setDate(today.getDate() - today.getDay()))
    for(var i = 0; i <35 ; i ++){
      this.calendar.push({
        month: start.getMonth(),
        day: start.getDate(),
        amount:Math.floor(Math.random() * 10)
      })
      start = new Date(start.setDate(start.getDate() + 1))
    }
    today = new Date()

    for(var i = 0; i < this.dateLimit ; i ++){
      this.date.push({
        month: today.getMonth(),
        day: today.getDate(),
        strval: (today.getMonth()+1)+ "/"+today.getDate()
      })
      today = new Date(today.setDate(today.getDate() + 1))
    }
    this.reserveForm = {
      interval:this.interval[0],
      name:"",
      phone:"",
      amount:1,
      date:this.date[0].strval
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
      reserveForm:{}
    };
  },
  methods:{
    sendReserve: function () {
      console.log(this.reserveForm)
      Swal.fire({
          title: '預約成功',
          text: this.reserveForm.name + "先生/小姐您好，您的預約時間為" + this.reserveForm.date + " " + this.reserveForm.interval+"。人數為"+this.reserveForm.amount+ "人。 已預約成功，位子將保留十分鐘。",
          icon: 'success',
          confirmButtonText: '確定'
      })
    }
  }
}).mount("#app");
