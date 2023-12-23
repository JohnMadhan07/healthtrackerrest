<template id="user-profile">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        User Profile
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
          <div class="card-footer text-center">
            <div v-if="user">
              <a :href="`/users/${user.id}/activities`">View User Activities</a>
            </div>
          </div>
        </form>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"];
    const url = `/api/users/${userId}`
    axios.get(url)
        .then(res => this.user = res.data)
        .catch(() => alert("Error while fetching user" + userId));
  }
});
</script>