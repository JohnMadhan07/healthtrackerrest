package ie.setu.domain

data class Diet(val id: Int,
                 val food:String,
                 val calories:Int,
                 var userId: Int)