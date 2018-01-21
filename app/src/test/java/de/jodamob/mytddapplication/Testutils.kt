package de.jodamob.mytddapplication

import android.annotation.SuppressLint
import android.app.Application
import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.support.v4.app.SupportActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.internal.util.MockUtil


fun Any.isSpy() = MockUtil.isSpy(this)
fun Any.isMock() = MockUtil.isMock(this)

fun <T: SupportActivity> prepareForTest(activity: T): T {
    return activity.apply {
        if (!(isSpy() or isMock())) {
            return prepareForTest(spy(this))
        }
        val mockFragmentManager = mockFragmentManager()
        whenever(fragmentManager).thenReturn(mockFragmentManager)
        val mockApplication = mock<Application>()
        whenever(application).thenReturn(mockApplication)
    }
}

@SuppressLint("CommitTransaction", "NewApi")
fun mockFragmentManager(transaction: FragmentTransaction = mock<FragmentTransaction>()): FragmentManager {
    return transaction.run {
        whenever(replace(anyInt(), any<Fragment>())).thenReturn(this)
        whenever(replace(anyInt(), any<Fragment>(), anyString())).thenReturn(this)
        whenever(remove(any<Fragment>())).thenReturn(this)
        whenever(addToBackStack(anyString())).thenReturn(this)
        whenever(add(any<Fragment>(), anyString())).thenReturn(this)
        whenever(add(anyInt(), any<Fragment>())).thenReturn(this)
        whenever(add(anyInt(), any<Fragment>(), anyString())).thenReturn(this)
        whenever(setCustomAnimations(anyInt(), anyInt())).thenReturn(this)
        whenever(setCustomAnimations(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(this)
        mock<FragmentManager>().apply {
            whenever(beginTransaction()).thenReturn(transaction)
        }
    }
}
