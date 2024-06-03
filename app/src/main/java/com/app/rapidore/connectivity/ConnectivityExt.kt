package com.app.rapidore.connectivity

import com.app.rapidore.connectivity.base.ConnectivityProvider

 fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
    return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
}