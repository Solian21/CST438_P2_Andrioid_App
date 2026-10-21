package com.example.cst438_p2_android_app.network
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {
    @GET("api/v1/users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") authorization: String
    ): UserResponse

    @DELETE("api/v1/users/me")
    suspend fun deleteCurrentUser(
        @Header("Authorization") authorization: String
    ): Response<Unit>

    @GET("api/v1/users")
    suspend fun getUsers(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): UserPageResponse

    @PATCH("api/v1/users/{userId}")
    suspend fun updateUserRole(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Long,
        @Body request: UserRoleUpdate
    ): UserResponse

    @DELETE("api/v1/users/{userId}")
    suspend fun deleteUser(
        @Header("Authorization") authorization: String,
        @Path("userId") userId: Long
    ): Response<Unit>

    @GET("api/v1/meal-plans")
    suspend fun getMealPlans(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): MealPlanPageResponse

    @GET("api/v1/recipes")
    suspend fun getRecipes(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("sort") sort: String = "createdAt,desc"
    ): RecipePageResponse

    @GET("api/v1/ingredients")
    suspend fun getIngredients(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): IngredientPageResponse

    @POST("api/v1/ingredients")
    suspend fun createIngredient(
        @Header("Authorization") authorization: String,
        @Body request: IngredientCreate
    ): IngredientResponse

    @POST("api/v1/recipes")
    suspend fun createRecipe(
        @Header("Authorization") authorization: String,
        @Body request: RecipeCreate
    ): RecipeResponse
    @GET("api/v1/recipes/{recipeId}")
    suspend fun getRecipe(
        @Header("Authorization") authorization: String,
        @Path("recipeId") recipeId: Long
    ): RecipeResponse
    @POST("api/v1/meal-plans")
    suspend fun createMealPlan(
        @Header("Authorization") authorization: String,
        @Body request: MealPlanCreate
    ): MealPlanResponse

    @GET("api/v1/meal-plans/{mealPlanId}")
    suspend fun getMealPlan(
        @Header("Authorization") authorization: String,
        @Path("mealPlanId") mealPlanId: Long
    ): MealPlanResponse

    @PATCH("api/v1/meal-plans/{mealPlanId}")
    suspend fun updateMealPlan(
        @Header("Authorization") authorization: String,
        @Path("mealPlanId") mealPlanId: Long,
        @Body request: MealPlanPatch
    ): MealPlanResponse

    @DELETE("api/v1/meal-plans/{mealPlanId}")
    suspend fun deleteMealPlan(
        @Header("Authorization") authorization: String,
        @Path("mealPlanId") mealPlanId: Long
    ): Response<Unit>
}
