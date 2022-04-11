Vue.createApp({
	data() {

		return {
			categorys: [
				// 分類 請用 api 讀取並取代此預設值
				{
					id: "10000",
					name: "期間限定",
					href: "limited-content",
					img: "../images/limited.png",
					msg: "※商品數量有限，依各店鋪實際販售情況會有所不同，如提前完售敬請見諒​",
				},
				{
					id: "c02",
					name: "外帶菜單",
					href: "takeaway-content",
					img: "../images/takeaway.png",
					msg: "※外帶的了菜，帶不走我對你的愛​",
				},
				{
					id: "c03",
					name: "握壽司",
					href: "sushi-content",
					img: "../images/sushi.png",
					msg: "※想握的​只有一個，就是妳的手",
				},
			],

			foods: [
				//   // 食物 請用 api 讀取並取代此預設值

				//   {
				//     id: "f01",
				//     cid: "c01",
				//     name: "極上鮪魚大腹",
				//     price: "40",
				//     img: "images/sushiMenu/limited/極上鮪魚大腹.png",
				//   },
				//   {
				//     id: "f02",
				//     cid: "c02",
				//     name: "極上鮪魚中腹",
				//     price: "40",
				//     img: "images/sushiMenu/limited/極上鮪魚大腹.png",
				//   },
				//   {
				//     id: "f03",
				//     cid: "c01",
				//     name: "極上鮪魚小腹",
				//     price: "40",
				//     img: "images/sushiMenu/limited/極上鮪魚大腹.png",
				//   },
			],
			cart: [],
		};
	},
	methods: {
		getFoods: function(cid) {
			return this.foods.filter((food) => food.cid == cid);
		},
		orderFood: function(food) {
			if (
				this.cart.find((foodInCart) => {
					return foodInCart.id == food.id;
				})
			) {
				this.cart.find((foodInCart) => {
					return foodInCart.id == food.id;
				}).amount += 1;
			} else {
				this.cart.push({
					id: food.id,
					price: food.price,
					name: food.name,
					amount: 1,
				});
			}
			Swal.fire({
				icon: 'success',
				toast: true,
				timer: 2000,
				position: 'top-end',
				showConfirmButton: false,
				title: food.name + '已添加至購物車'
			})
		},
		addOrder: function(food) {
			this.cart.find((foodInCart) => {
				return foodInCart.id == food.id;
			}).amount += 1;
		},
		reduceOrder: function(food) {
			if (
				this.cart.find((foodInCart) => {
					return foodInCart.id == food.id;
				}).amount == 1
			) {
				this.cart = this.cart.filter((foodInCart) => {
					return foodInCart.id != food.id;
				});
			} else {
				this.cart.find((foodInCart) => {
					return foodInCart.id == food.id;
				}).amount -= 1;
			}
		},
		sendCart: function() {

			// call 送出成功API
			Swal.fire({
				title: '訂單送出成功',
				text: '您的訂單已經成功送出',
				icon: 'success',
				confirmButtonText: '確定'
			})

			hideCartModal()
			this.cart = []

		}
	},
	mounted: function() {
		axios.get("http://localhost:8081/profile/cart")
			.then((response) => {
				console.log(response);
				let cart = response.data;
				cart.forEach(data => {
					this.cart.push({
						id: data.products.pid,
						price: data.products.price,
						name: data.products.productname,
						amount: data.quantity
					})
				})

			})
		axios.get("http://localhost:8081/profile/products")
			.then((response) => {

				let products = response.data;
				console.log(products);
				products.forEach(data => {
					this.foods.push({
						id: data.pid,
						cid: data.category.categoryId,
						name: data.productname,
						price: data.productprice,
						img: "../images/sushiMenu/limited/極上鮪魚大腹.png"
					})
				});
				console.log(this.foods);
			})
		axios.get("http://localhost:8081/profile/category")
			.then((response) => {
				let category = response.data
				console.log(category);
				category.forEach(data => {
					this.categorys.push({
						id: data.categoryId,
						name: data.categoryname,
						img: "../images/limited.png",
						msg: "※商品數量有限，依各店鋪實際販售情況會有所不同，如提前完售敬請見諒​"
					})
				})
			})
	}

	,
	computed: {
		total: function() {
			return this.cart.reduce(
				(acc, current) => acc + current.amount * current.price,
				0
			);
		},
	},
}).mount("#app");
