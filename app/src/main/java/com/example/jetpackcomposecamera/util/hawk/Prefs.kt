package com.example.jetpackcomposecamera.util.hawk

object Prefs {

    private val pref: Pref = HawkImpl

    init {
        pref.init()
    }

    private const val PREF_KEY_SHOW_ON_BOARDING_STATE = "showOnBoardingState"
    private const val  PREF_KEY_STAY_IN_STATE = "rememberMeState"
    private const val  PREF_KEY_USERNAME = "usernameState"

    fun isShowedOnBoarding(): Boolean {
        return pref.get(PREF_KEY_SHOW_ON_BOARDING_STATE, false)
    }

    fun setShowOnBoardingState(showed: Boolean) {
        pref.put(PREF_KEY_SHOW_ON_BOARDING_STATE, showed)
    }

    fun isStayIn():Boolean {
        return pref.get(PREF_KEY_STAY_IN_STATE,false)
    }

    fun setStayIn(isStay:Boolean){
        pref.put(PREF_KEY_STAY_IN_STATE,isStay)
    }

    fun setUsername(username:String){
        pref.put(PREF_KEY_USERNAME,username)
    }

    fun getUsername():String = pref.get(PREF_KEY_USERNAME,"")
}