<template id="activity-overview">
  <div>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Activities
          </div>
        </div>
      </div>
    </div>
    <div>
      <ul class="activity-overview-list">
        <li v-for="activity in activities">
          {{ activity.description }}
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
app.component("activity-overview", {
  template: "#activity-overview",
  data: () => ({
    activities: [],
  }),
  created() {
    this.fetchActivities();
  },
  methods: {
    fetchActivities: function () {
      axios.get("/api/activities")
          .then(res => this.activities = res.data)
          .catch(() => alert("Error while fetching users"));
    }
  }
});
</script>