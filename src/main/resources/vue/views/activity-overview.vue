<template id="activity-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Activities
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="card-body" :class="{ 'd-none': hideForm}">
      <form id="addActivity">
        <!-- Assuming user ID is entered by the user -->
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-userId">User ID</span>
          </div>
          <input type="text" class="form-control" v-model="formData.userId" name="userId" placeholder="User ID"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-description">Description</span>
          </div>
          <input type="text" class="form-control" v-model="formData.description" name="name" placeholder="Description"/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-activity-duration">Duration</span>
          </div>
          <input type="text" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration in mins"/>
        </div>
      </form>
      <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addActivity()">
        Add Activity
      </button>
    </div>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="(activity,index) in activities" :key="index">
        <div class="mr-auto p-2">
          <span><a :href="`/activities/${activity.id}`"> {{ activity.description }}</a></span>
        </div>
      </div>
    </div>
  </app-layout>
</template>
<script>
app.component("activity-overview", {
  template: "#activity-overview",
  data: () => ({
    activities: [],
    formData: [],
    hideForm: true
  }),
  created() {
    this.fetchActivities();
  },
  methods: {
    fetchActivities: function () {
      axios.get("/api/activities")
          .then(res => this.activities = res.data)
          .catch(() => alert("Error while fetching activities"));
    },
    addActivity: function () {
      const url = `/api/activities`;
      axios.post(url,
          {
            description: this.formData.description,
            duration: this.formData.duration
          })
          .then(response => {
            this.activities.push(response.data)
            this.hideForm= true;
            window.location.href = '/activities';
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
});
</script>
