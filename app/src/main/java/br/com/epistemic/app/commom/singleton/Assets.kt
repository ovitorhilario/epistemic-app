package br.com.epistemic.app.commom.singleton

object Assets {
    val EpistemicLogo = buildString { append(AppConfig.assetFolder, "epistemic_logo.png") }
    val EpistemicIcon = buildString { append(AppConfig.assetFolder, "epistemic_icon.png") }
    val AvatarWoman = buildString { append(AppConfig.assetFolder, "avatar_woman.png") }
    val AvatarMan = buildString { append(AppConfig.assetFolder, "avatar_man.png") }
    val EpilepsyIllustration = buildString { append(AppConfig.assetFolder, "epilepsy_illustration.png") }
}