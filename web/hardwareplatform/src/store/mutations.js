export default{
	setUserRouter (state, routes) {
        state.userRouters = routes
    },
    setSubCabsNo(state,routes){
        state.subCabs.No = routes.No
    },
    setCanInfo (state,p) {
        state.cabinfo = p
    },
    setNetworkInfo (state,p) {
        state.networkinfo = p
    }
}
