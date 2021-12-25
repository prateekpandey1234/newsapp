package util
//this file here is used to tell whether we get a successful response or error response
sealed class Resource<T>(
    val data: T? = null,//the T here is an generic type
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)//we wil only initialize data here cuz we get no error
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)//we will initialize both of them
    class Loading<T> : Resource<T>()
}