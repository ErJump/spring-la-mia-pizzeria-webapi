<template>
  <div id="app" class="container-lg">
    <div class="row">
      <div class="col-12">
        <h1>Pizze</h1>
      </div>
      <div class="card col-4"  v-for="pizza in pizzas" :key="pizza.id">
        <div class="card-body" v-if="pizzaEditId != pizza.id">
          <h5 class="card-title">{{ pizza.name }}</h5>
          <h6 class="card-subtitle mb-2 text-muted">{{ pizza.price }} $</h6>
          <p class="card-text">{{ pizza.description }}</p>
          <button class="btn btn-primary" @click="editPizza(pizza.id)">Modifica</button>
          <button v-if="!pizza.ingredients" @click="getIngredients(pizza.id)" class="btn btn-success me-2">Ingredienti</button>
          <ul class="card-text" v-else>
            <li v-for="ingredient in pizza.ingredients" :key="ingredient.id">{{ ingredient.name }} </li>
          </ul>
        </div>
        <div v-else class="card-body">
          <form @submit="updatePizza">
            <label for="name">Nome</label>
            <input type="text" id="name" name="name" class="form-control mb-2" v-model="pizza.name">
            <label for="price">Prezzo</label>
            <input type="number" id="price" name="price" class="form-control mb-2" v-model="pizza.price">
            <label for="description">Descrizione</label>
            <input type="text" id="description" name="description" class="form-control mb-2" v-model="pizza.description">
            <input type="submit" value="Aggiorna" class="btn btn-success me-2">
            <button @click="editPizza(-1)" class="btn btn-danger">Annulla</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const pizzaEditIdConst = -1

export default {
  name: 'App',
  data() {
    return {
      apiUrl: 'http://localhost:8080/api/1/',
      pizzas: [],
      pizzaEditId: pizzaEditIdConst,
    }
  },
  methods: {
    getPizzas() {
      axios.get(this.apiUrl + "pizza/allPizzas")
        .then(response => {
          this.pizzas = response.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getPizzaIndexById(id) {
      return this.pizzas.findIndex(pizza => pizza.id === id)
    },
    editPizza(id) {
      this.pizzaEditId = id
    },
    updatePizza(event){
      event.preventDefault()

      const pizzaId = this.pizzaEditId
      const pizzaIndex = this.getPizzaIndexById(pizzaId)
      const pizza = this.pizzas[pizzaIndex]

      //console.log("pizza: " + JSON.stringify(pizza))

      this.editPizza(pizzaEditIdConst)

      axios.post(this.apiUrl + "pizza/update/" + pizzaId, pizza)
        .then(response => {
          const index = this.getPizzaIndexById(pizzaId)
          const oldP = this.pizzas[index]
          this.pizzas[pizzaIndex] = response.data
          this.pizzas[pizzaIndex].ingredients = oldP.ingredients
        })
        .catch(error => {
          console.log(error)
        })
    },
    getIngredients(pizzaId) {
      axios.get(this.apiUrl + "ingredienti/by/pizza/" + pizzaId)
        .then(response => {
          const ingredients = response.data

          if (ingredients == null) return
          
          const index = this.getPizzaIndexById(pizzaId)
          this.pizzas[index].ingredients = ingredients
          console.log(this.pizzas[index].ingredients)
        })
        .catch(error => {
          console.log(error)
        })
    }
  },
  mounted() {
    this.getPizzas()
  }
}
</script>

<style lang="scss">

</style>
