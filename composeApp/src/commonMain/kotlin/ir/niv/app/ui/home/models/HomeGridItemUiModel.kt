package ir.niv.app.ui.home.models

import androidx.compose.ui.graphics.Color
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.apple_whole_24
import nivapp.composeapp.generated.resources.dumbbell_fitness_24
import nivapp.composeapp.generated.resources.gym_24
import nivapp.composeapp.generated.resources.home_grid_coach_label
import nivapp.composeapp.generated.resources.home_grid_exercise_label
import nivapp.composeapp.generated.resources.home_grid_food_label
import nivapp.composeapp.generated.resources.home_grid_friend_label
import nivapp.composeapp.generated.resources.home_grid_gym_label
import nivapp.composeapp.generated.resources.home_grid_workout_label
import nivapp.composeapp.generated.resources.stationary_bike_24
import nivapp.composeapp.generated.resources.user_coach_24
import nivapp.composeapp.generated.resources.users_24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class HomeGridItemUiModel(
    val type: Type,
    val badge: Int = 0,
) {
    enum class Type(
        val label: StringResource,
        val icon: DrawableResource,
        val backgroundColor: Color,
        val iconTint: Color,
    ) {
        Gym(
            Res.string.home_grid_gym_label,
            Res.drawable.stationary_bike_24,
            Color(0xFFcefce1),
            Color(0xFF28af62),
        ),
        Workout(
            Res.string.home_grid_workout_label,
            Res.drawable.dumbbell_fitness_24,
            Color(0xFFfacfd6),
            Color(0xFFf33d5b),
        ),
        Food(
            Res.string.home_grid_food_label,
            Res.drawable.apple_whole_24,
            Color(0xFFd2e3fa),
            Color(0xFF3080ed),
        ),
        Coach(
            Res.string.home_grid_coach_label,
            Res.drawable.user_coach_24,
            Color(0xFFd7d7f9),
            Color(0xFF5556ce),
        ),
        Exercise(
            Res.string.home_grid_exercise_label,
            Res.drawable.gym_24,
            Color(0xFFfbd2e1),
            Color(0xFFb3446c),
        ),
        Friend(
            Res.string.home_grid_friend_label,
            Res.drawable.users_24,
            Color(0xFFf2d9fb),
            Color(0xFFaa20dd),
        ),
    }

    companion object {
        fun getItems() = Type.entries.map {
            HomeGridItemUiModel(it, 0)
        }
    }
}