package com.example.aero.ui.screens.old

//class Steps(): Screen {
//    @Composable
//    override fun Content() {
//        DebugLogger.log(
//            "Steps Recomposed",
//            Color.Green
//        )
//        val providerType = ProviderSettings.getProvider()
//        val context = LocalContext.current
//        val activeProvider = when(providerType) {
//            ProviderType.GOOGLE_HEALTH -> GoogleHealthProvider(context)
//            ProviderType.SAMSUNG_HEALTH -> SamsungHealthProvider(context)
//            ProviderType.STRAVA -> StravaProvider()
//            ProviderType.UNIFIED -> UnifiedProvider(context)
//        }
//        Background() {
//            val extension = when (activeProvider.type) {
//
//                ProviderType.GOOGLE_HEALTH -> listOf(
//                    Color(0xFFFF5722),
//                    Color(0xAAE24A75),
//                    Color.Transparent
//                )
//
//                ProviderType.SAMSUNG_HEALTH -> listOf(
//                    Color(0xFF0B5048),
//                    Color(0xFF72BB67),
//                    Color.Transparent
//                )
//
//                ProviderType.STRAVA -> listOf(
//                    Color(0xFFFC4C02),
//                    Color(0xFFFF8C42),
//                    Color.Transparent
//                )
//                ProviderType.UNIFIED -> listOf(
//                    Color(0xFFA6A6D3),
//                    Color(0xFFCAB5BA),
//                    Color.Transparent
//                )
//
//                else -> listOf(
//                    Color(0xFF0B5048),
//                    Color(0xFF72BB67),
//                    Color.Transparent
//                )
//            }
//
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//
//            ) {
//
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .background(
//                            Brush.verticalGradient(
//                                colors = extension
//                            )
//                        )
//                ) {}
//            }
//        }
//    }
//}