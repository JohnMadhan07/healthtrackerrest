<template id="user-profile">
  <app-layout>
    <div v-if="noUserFound">
      <p> We're sorry, we were not able to retrieve this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="user && !noUserFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Profile </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateUser()"> Update User
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="user.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="user.email" name="email" placeholder="Email"/>
          </div>
        </form>
      </div>
      <div class="card-footer text-center">
        <div v-if="user">
          <a :href="`/users/${user.id}/activities`">View User Activities</a>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null,
    noUserFound: false,
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"];
    const url = `/api/users/${userId}`;
    axios.get(url)
        .then(res => {
          if (res.data === '') {
            // Handle the case where the response data is empty (user not found)
            console.log("User not found");
            this.noUserFound = true;
          } else {
            // Process the response data when a user is found
            this.user = res.data;
            console.log("User found:", this.user);
          }
        })
        .catch(error => {
          console.log("Error in Axios request:", error);
        });
  },
      methods: {
        updateUser: function () {
          const userId = this.$javalin.pathParams["user-id"];
          const url = `/api/users/${userId}`
          axios.patch(url,
              {
                name: this.user.name,
                email: this.user.email
              })
              .then(response =>
                  this.user.push(response.data))
              .catch(error => {
                console.log(error)
              })
          alert("User updated!")
        }
      }
}

);
</script>