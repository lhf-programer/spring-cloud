// 生产错误日志
const errLog = {
  state: {
    errLog: []
  },
  pushLog(log) {
    this.state.errLog.unshift(log)
  },
  clearLog() {
    this.state.errLog = [];
  }
};

export default errLog;
