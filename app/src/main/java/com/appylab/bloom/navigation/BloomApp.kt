package com.appylab.bloom.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appylab.bloom.R
import com.appylab.bloom.core.ui.screenBrush
import com.appylab.bloom.feature.auth.LoginScreen
import com.appylab.bloom.feature.auth.SignUpScreen
import com.appylab.bloom.feature.dashboard.DashboardScreen
import com.appylab.bloom.feature.food.BarcodeScannerScreen
import com.appylab.bloom.feature.food.FoodScreen
import com.appylab.bloom.feature.food.FoodSearchScreen
import com.appylab.bloom.feature.food.ManualFoodEntryScreen
import com.appylab.bloom.feature.onboarding.OnboardingViewModel
import com.appylab.bloom.feature.onboarding.screens.Onboarding10Motivational
import com.appylab.bloom.feature.onboarding.screens.Onboarding11MealPlanScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding12RunScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding1WelcomeScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding2GoalScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding3Motivational
import com.appylab.bloom.feature.onboarding.screens.Onboarding4HabitsScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding5Motivational
import com.appylab.bloom.feature.onboarding.screens.Onboarding6ActivityScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding7AboutYouScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding8BodyStatsScreen
import com.appylab.bloom.feature.onboarding.screens.Onboarding9WeeklyGoalScreen
import com.appylab.bloom.feature.paywall.PaywallScreen
import com.appylab.bloom.feature.profile.ProfileScreen
import com.appylab.bloom.feature.profile.SettingsScreen
import com.appylab.bloom.feature.run.RunScreen
import com.appylab.bloom.feature.run.StepHistoryScreen
import com.appylab.bloom.feature.workout.AiWorkoutSummaryScreen
import com.appylab.bloom.feature.workout.ExerciseSearchScreen
import com.appylab.bloom.feature.workout.WorkoutScreen
import com.appylab.bloom.feature.workout.WorkoutSessionScreen
import kotlinx.coroutines.delay

@Composable
fun BloomApp(
    appViewModel: BloomAppViewModel = hiltViewModel()
) {
    var showSplash by remember { mutableStateOf(true) }
    val onboardingComplete by appViewModel.onboardingComplete.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        delay(SplashDurationMillis)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
        return
    }

    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    val startDestination = if (onboardingComplete) RouteDashboard else RouteSignUp

    NavHost(navController = navController, startDestination = startDestination) {
        composable(RouteSignUp) {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(RouteLogin) },
                onNavigateToOnboarding = {
                    navController.navigate(RouteOnboarding1) {
                        popUpTo(RouteSignUp) { inclusive = true }
                    }
                }
            )
        }
        composable(RouteLogin) {
            LoginScreen(
                onNavigateToSignUp = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.navigate(RouteDashboard) {
                        popUpTo(RouteSignUp) { inclusive = true }
                    }
                }
            )
        }
        composable(RouteOnboarding1) { Onboarding1WelcomeScreen(onboardingViewModel) { navController.navigate(RouteOnboarding2) } }
        composable(RouteOnboarding2) { Onboarding2GoalScreen(onboardingViewModel) { navController.navigate(RouteOnboarding3) } }
        composable(RouteOnboarding3) { Onboarding3Motivational { navController.navigate(RouteOnboarding4) } }
        composable(RouteOnboarding4) { Onboarding4HabitsScreen(onboardingViewModel) { navController.navigate(RouteOnboarding5) } }
        composable(RouteOnboarding5) { Onboarding5Motivational { navController.navigate(RouteOnboarding6) } }
        composable(RouteOnboarding6) { Onboarding6ActivityScreen(onboardingViewModel) { navController.navigate(RouteOnboarding7) } }
        composable(RouteOnboarding7) { Onboarding7AboutYouScreen(onboardingViewModel) { navController.navigate(RouteOnboarding8) } }
        composable(RouteOnboarding8) { Onboarding8BodyStatsScreen(onboardingViewModel) { navController.navigate(RouteOnboarding9) } }
        composable(RouteOnboarding9) { Onboarding9WeeklyGoalScreen(onboardingViewModel) { navController.navigate(RouteOnboarding10) } }
        composable(RouteOnboarding10) { Onboarding10Motivational { navController.navigate(RouteOnboarding11) } }
        composable(RouteOnboarding11) { Onboarding11MealPlanScreen(onboardingViewModel) { navController.navigate(RouteOnboarding12) } }
        composable(RouteOnboarding12) {
            Onboarding12RunScreen(onboardingViewModel) {
                navController.navigate(RoutePaywall) {
                    popUpTo(RouteOnboarding1) { inclusive = true }
                }
            }
        }
        composable(RoutePaywall) {
            PaywallScreen(
                onTrialStarted = {
                    navController.navigate(RouteDashboard) {
                        popUpTo(RoutePaywall) { inclusive = true }
                    }
                }
            )
        }
        dashboardRoutes(navController)
    }
}

private fun androidx.navigation.NavGraphBuilder.dashboardRoutes(navController: NavHostController) {
    composable(RouteDashboard) {
        val dashboardViewModel: com.appylab.bloom.feature.dashboard.DashboardViewModel = hiltViewModel()
        val uiState by dashboardViewModel.uiState.collectAsState()
        DashboardScreen(
            uiState = uiState,
            onDestinationSelected = { destination -> navController.navigateToTab(destination) },
            onNavigateToProfile = { navController.navigate(RouteProfile) }
        )
    }
    composable(RouteWorkout) {
        val workoutViewModel: com.appylab.bloom.feature.workout.WorkoutViewModel = hiltViewModel()
        WorkoutScreen(
            viewModel = workoutViewModel,
            onDestinationSelected = { destination -> navController.navigateToTab(destination) },
            onStartSession = { navController.navigate(RouteWorkoutSession) },
            onViewHistory = { }
        )
    }
    composable(RouteWorkoutSession) {
        val workoutViewModel: com.appylab.bloom.feature.workout.WorkoutViewModel = hiltViewModel()
        LaunchedEffect(Unit) { workoutViewModel.startSession() }
        WorkoutSessionScreen(
            viewModel = workoutViewModel,
            onAddExercise = { navController.navigate(RouteWorkoutSearch) },
            onFinishSession = {
                navController.navigate(RouteWorkoutSummary) {
                    popUpTo(RouteWorkout) { inclusive = false }
                }
            },
            onCancel = { navController.popBackStack() }
        )
    }
    composable(RouteWorkoutSearch) {
        val sessionEntry = remember(navController) { navController.getBackStackEntry(RouteWorkoutSession) }
        val workoutViewModel: com.appylab.bloom.feature.workout.WorkoutViewModel = hiltViewModel(sessionEntry)
        ExerciseSearchScreen(
            viewModel = workoutViewModel,
            onBack = { navController.popBackStack() },
            onExerciseSelected = { exercise ->
                workoutViewModel.addExerciseToSession(exercise) {
                    navController.popBackStack()
                }
            }
        )
    }
    composable(RouteWorkoutSummary) {
        AiWorkoutSummaryScreen(onDismiss = { navController.popBackStack() })
    }
    composable(RouteFood) {
        FoodScreen(
            onDestinationSelected = { destination -> navController.navigateToTab(destination) },
            onNavigateToSearch = { navController.navigate(RouteFoodSearch) },
            onNavigateToBarcode = { navController.navigate(RouteFoodBarcode) },
            onNavigateToManual = { navController.navigate(RouteFoodManual) }
        )
    }
    composable(RouteFoodSearch) {
        FoodSearchScreen(
            onBack = { navController.popBackStack() },
            onFoodSelected = { }
        )
    }
    composable(RouteFoodManual) {
        ManualFoodEntryScreen(onBack = { navController.popBackStack() })
    }
    composable(RouteFoodBarcode) {
        BarcodeScannerScreen(
            onBarcodeScanned = { navController.popBackStack() }
        )
    }
    composable(RouteRun) {
        val runViewModel: com.appylab.bloom.feature.run.RunViewModel = hiltViewModel()
        RunScreen(
            viewModel = runViewModel,
            onDestinationSelected = { destination -> navController.navigateToTab(destination) },
            onNavigateToHistory = { navController.navigate(RouteRunHistory) }
        )
    }
    composable(RouteRunHistory) {
        StepHistoryScreen(onBack = { navController.popBackStack() })
    }
    composable(RouteProfile) {
        ProfileScreen(
            onBack = { navController.popBackStack() },
            onNavigateToSettings = { navController.navigate(RouteSettings) }
        )
    }
    composable(RouteSettings) {
        SettingsScreen(onBack = { navController.popBackStack() })
    }
}

private fun NavHostController.navigateToTab(destination: AppDestination) {
    val route = when (destination) {
        AppDestination.Dashboard -> RouteDashboard
        AppDestination.Workout -> RouteWorkout
        AppDestination.Food -> RouteFood
        AppDestination.Run -> RouteRun
        AppDestination.Profile -> RouteProfile
    }
    navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(RouteDashboard) { saveState = true }
    }
}

@Composable
private fun SplashScreen() {
    Image(
        painter = painterResource(R.drawable.splash_screen),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush()),
        contentScale = ContentScale.Crop
    )
}

private const val SplashDurationMillis = 3_000L

private const val RouteSignUp = "auth/signup"
private const val RouteLogin = "auth/login"
private const val RouteOnboarding1 = "onboarding/1"
private const val RouteOnboarding2 = "onboarding/2"
private const val RouteOnboarding3 = "onboarding/3"
private const val RouteOnboarding4 = "onboarding/4"
private const val RouteOnboarding5 = "onboarding/5"
private const val RouteOnboarding6 = "onboarding/6"
private const val RouteOnboarding7 = "onboarding/7"
private const val RouteOnboarding8 = "onboarding/8"
private const val RouteOnboarding9 = "onboarding/9"
private const val RouteOnboarding10 = "onboarding/10"
private const val RouteOnboarding11 = "onboarding/11"
private const val RouteOnboarding12 = "onboarding/12"
private const val RoutePaywall = "paywall"
private const val RouteDashboard = "dashboard"
private const val RouteWorkout = "workout"
private const val RouteWorkoutSession = "workout/session"
private const val RouteWorkoutSearch = "workout/search"
private const val RouteWorkoutSummary = "workout/summary"
private const val RouteFood = "food"
private const val RouteFoodSearch = "food/search"
private const val RouteFoodManual = "food/manual"
private const val RouteFoodBarcode = "food/barcode"
private const val RouteRun = "run"
private const val RouteRunHistory = "run/history"
private const val RouteProfile = "profile"
private const val RouteSettings = "settings"
