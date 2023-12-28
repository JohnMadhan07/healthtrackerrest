<template id="activity-profile">
  <app-layout>
    <div v-if="noActivityFound">
      <p> We're sorry, we were not able to retrieve this Activity.</p>
      <p> View <a :href="'/activities'">all Activities</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noActivityFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Activity Profile </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">Activity ID</span>
            </div>
            <input type="number" class="form-control" v-model="activity.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-description">Activity Description</span>
            </div>
            <input type="text" class="form-control" v-model="activity.description" name="description" placeholder="Description"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-duration">Duration(in mins)</span>
            </div>
            <input type="text" class="form-control" v-model="activity.duration" name="duration" placeholder="Duration"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-calories">Calories</span>
            </div>
            <input type="text" class="form-control" v-model="activity.calories" name="calories" placeholder="calories"/>
          </div>
        </form>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("activity-profile", {
  template: "#activity-profile",
  data: () => ({
    activity: null,
    noActivityFound: false,
  }),
  created: function () {
    const activityid = this.$javalin.pathParams["activity-id"];
    const url = `/api/activities/${activityid}`
    axios.get(url)
        .then(res => this.activity = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noActivityFound = true
        })
  }
});
</script>