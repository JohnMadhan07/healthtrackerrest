package ie.setu.domain


data class Supplement (val id: Int,
                 val name:String,
                 val about:String,
                 var userId: Int)