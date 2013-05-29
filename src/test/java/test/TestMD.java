package test;

import org.bridj.BridJ;
import org.bridj.Pointer;
import ksmdapija.*;

import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: trade
 * Date: 13-4-10
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public class TestMD
{
    public static void main( String[] argv )
    {
        System.out.println( "Start TestMD------------------------" ) ;

        String frontAddr = "tcp://10.108.10.10:13159" ;	// 需要申请，请修改
        String brokerID = "111111" ; // 需要申请，请修改
        String userID = "8008" ;     // 需要申请，请修改
        String password = "123456" ; // 需要申请，请修改


        BridJ.register(CThostFtdcMdApi.class);    // 必须的

        Pointer<CThostFtdcMdApi> PointerThostFtdcMdApi = CThostFtdcMdApi.CreateFtdcMdApi( Pointer.pointerToCString( "./log" ) , false
                                                                                          ) ;
        CThostFtdcMdApi ftdcMdApi = PointerThostFtdcMdApi.get( ) ;

        /**
         * 如果不加入这段代码，会导致 BridJ类中的public static synchronized Object getJavaObjectFromNativePeer(long peer) {
         * 获取不到strongNativeObjects的对应对象。
         */
        BridJ.protectFromGC( ftdcMdApi ) ;        // 必须的

        CThostFtdcMdSpi mdSpi = new TestMdSpi( ftdcMdApi , brokerID , userID , password ) ;

        /**
         * 如果不加入这段代码，会导致 BridJ类中的public static synchronized Object getJavaObjectFromNativePeer(long peer) {
         * 获取不到strongNativeObjects的对应对象。
         */
        BridJ.protectFromGC( mdSpi ) ;            // 必须的

        ftdcMdApi.RegisterSpi( Pointer.pointerTo( mdSpi ) ) ;

        ftdcMdApi.RegisterFront( Pointer.pointerToCString( frontAddr ) );
        ftdcMdApi.Init();

        ftdcMdApi.Join( ) ;

        System.out.println( "End TestMD------------------------" ) ;
    }
}
