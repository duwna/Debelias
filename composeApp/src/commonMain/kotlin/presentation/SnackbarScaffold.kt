package presentation

//@Composable
//fun SnackbarScaffold(
//    messageHandler: MessageHandler,
//    content: @Composable () -> Unit
//) {
//
//    val scaffoldState = rememberScaffoldState()
//    val appContext = LocalContext.current.applicationContext
//
//    val defaultSnackbarColor = MaterialTheme.colorScheme.primary
//    val errorSnackbarColor = MaterialTheme.colorScheme.error
//    var snackbarColor by remember { mutableStateOf(defaultSnackbarColor) }
//
//    LaunchedEffect(Unit) {
//        messageHandler
//            .observeMessages()
//            .collect { event ->
//                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
//
//                val showJob = launch {
//                    snackbarColor = if (event is MessageEvent.Error) errorSnackbarColor else defaultSnackbarColor
//                    scaffoldState.snackbarHostState.showSnackbar(
//                        message = event.text(appContext),
//                        duration = SnackbarDuration.Indefinite
//                    )
//                }
//
//                launch {
//                    delay(event.durationMillis)
//                    showJob.cancel()
//                }
//            }
//    }
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        snackbarHost = { host ->
//            SnackbarHost(host) { data ->
//                Snackbar(
//                    snackbarData = data,
//                    backgroundColor = snackbarColor,
//                    modifier = Modifier.noRippleClickable {
//                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
//                    }
//                )
//            }
//        }
//    ) {
//        content()
//        it.toString()
//    }
//}
