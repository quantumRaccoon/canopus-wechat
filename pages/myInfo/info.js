// pages/myInfo/info.js
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isLogin:app.globalData.isLogin,
    isNeedLogin:false,
    code:'',
    userAvatar:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let _this = this
    wx.login({
      success(res) {
        if (res.code) {
          //发起网络请求
          wx.request({
            url: 'http://119.3.85.70:8085/canopus/wechat/user/getOpenId/' + res.code,
            method: 'POST', 
            success: function (e) {
              console.log(e)
              if (e.data.data.isFirst) {
                wx.getSetting({
                  success: res => {
                    if (res.authSetting['scope.userInfo']) {
                      // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                      wx.getUserInfo({
                        lang: "zh_CN",
                        success: res => {
                          // 可以将 res 发送给后台解码出 unionId
                            app.globalData.userInfo = res.userInfo
                            console.log(res)
                            app.globalData.isLogin = true
                            _this.setData({
                              isLogin: true,
                              isNeedLogin: false,
                              userAvatar: res.userInfo.avatarUrl,
                              userName: res.userInfo.nickName,
                            })
                        },
                        fail:function(res) {
                          console.log("can not get user info")
                          wx.showToast({
                            title: '获取用户信息失败，需要授权',
                            icon:'none',
                            duration: 2000,
                          })
                        },
                        })
                      }
                    },
                  fail: ()=>{
                    _this.setData({
                      isNeedLogin: true,
                      isLogin: false,
                    })
                  }
                })
              }
            },
            fail: function (res) {
              wx.showToast({
                title: '不能请求服务器',
                icon:'none',
                duration: 2000,
              })
            }
          })
        } else {
          wx.showToast({
            title: '登录失败！',
            icon: 'none',
            duration: 2000,
          })
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
   
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  onLoginClick: function() {
   
  },
  onGotUserInfo: function (e) {
    let _this = this;
    console.log(e.detail.errMsg)
    console.log(e.detail.userInfo)
    console.log(e.detail.rawData)
    app.globalData.isLogin = true
    _this.setData({
      isLogin: true,
    })
  },

})