package me.commiyou.xposed.nodialog.hook


import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.YukiHookAPI.Status.executorName
import com.highcapable.yukihookapi.YukiHookAPI.Status.executorVersion
import com.highcapable.yukihookapi.YukiHookAPI.Status.isXposedModuleActive
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.*
import com.highcapable.yukihookapi.hook.log.loggerD
import com.highcapable.yukihookapi.hook.log.loggerE
import com.highcapable.yukihookapi.hook.type.android.*
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringType
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import me.commiyou.xposed.xsearch.BuildConfig
import me.commiyou.xposed.xsearch.data.DataConst

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() {
        // 配置 YuKiHookAPI
        // 可简写为 configs {}
        YukiHookAPI.configs {
            debugTag = BuildConfig.APPLICATION_ID
            isEnableModulePrefsCache = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onHook() {
        YukiHookAPI.encase {
            loadApp {

                ActivityClass.hook {
                    injectMember {
                        method {
                            name = "onStart"
                            emptyParam()
                            returnType = UnitType
                        }

                        afterHook {
                            val pkgName = packageName
                            val clsName = instanceClass.name
                            val mtdName = method.name
                            if (!DataConst.ChromeActivityClasses.contains(clsName))
                                return@afterHook
                            loggerD(msg = "hook $pkgName $clsName $mtdName result $result ${ActivityClass.name}")

                            val clsLoader = instance<Activity>().classLoader

                            classOf(DataConst.TemplateUrlClass, clsLoader).hook(false) {
                                injectMember {
                                    method {
                                        name = "create"
                                        paramCount = 1
                                        modifiers {
                                            asStatic()
                                        }
                                    }
                                    afterHook {
                                        val pkgName = packageName
                                        val clsName = instanceClass.name
                                        val mtdName = method.name
                                        var str = "23";
                                        instanceClass.allMethods { index, method ->
                                            if (method.returnType != StringType) return@allMethods
                                            if (method.parameterCount != 0 ) return@allMethods
                                            loggerD(msg = "iter methods $index $method ${method.invoke(result)}")
                                        }

                                        loggerD(msg = "hook $pkgName $clsName $mtdName result $result toString $str")
                                        // method.invokeOriginal<() -> Unit>()
                                        result
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}