// pages/myInfo/info.js
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isLogin:!app.globalData.isLogin,
    code:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    let _this = this
    wx.login({
      success(res) {
        if (res.code) {
          //发起网络请求
          console.log(res.code);
          wx.request({
            url: 'http://119.3.85.70:8085/canopus/wechat/user/getOpenId/' + res.code,
            method: 'POST',
            success: function(e) {
              console.log(e)
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  },
  onGotUserInfo: function (e) {
    let _this = this;
    console.log(e.detail.errMsg)
    console.log(e.detail.userInfo)
    console.log(e.detail.rawData)
  },

})