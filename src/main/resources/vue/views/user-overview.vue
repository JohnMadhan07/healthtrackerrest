<template id="user-overview">
  <app-layout>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(user,index) in users" v-bind:key="index">
        <div class="mr-auto p-2">
          <span><a :href="`/users/${user.id}`"> {{ user.name }} ({{ user.email }})</a></span>
        </div>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("user-overview", {
  template: "#user-overview",
  data: () => ({
    users: [],
  }),
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => this.users = res.data)
          .catch(() => alert("Error while fetching users"));
    }
  }
});
</script>