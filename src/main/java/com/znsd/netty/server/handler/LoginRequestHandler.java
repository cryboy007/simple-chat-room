package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.request.LoginRequestPacket;
import com.znsd.netty.protocol.response.LoginResponsePacket;
import com.znsd.netty.session.Session;
import com.znsd.netty.util.IDUtil;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName LoginHandler
 * @Author HETAO
 * @Date 2021/5/18 17:03
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            Session session = new Session(userId, loginResponsePacket.getUserName());
            SessionUtil.bindSession(session,ctx.channel());
        }else {
            loginResponsePacket.setReason("账户密码错误");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + "["+loginRequestPacket.getUserName()+"]登录失败");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }
    //校验账户密码完整性
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    // 用户断线之后取消绑定
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
