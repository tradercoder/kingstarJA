package test;

import org.bridj.BridJ;
import org.bridj.Pointer;
import kstradeapija.*;

/**
 * Created with IntelliJ IDEA.
 * User: trade
 * Date: 13-5-16
 * Time: 下午10:25
 * To change this template use File | Settings | File Templates.
 */
public class TestTrade
{
    public static void main( String[] argv )
    {
        System.out.println( "Start TestTrade------------------------" ) ;

        BridJ.register(CThostFtdcTraderApi.class);    // 必须的

        Pointer<CThostFtdcTraderApi> PointerThostFtdcTraderApi = CThostFtdcTraderApi.CreateFtdcTraderApi( Pointer.pointerToCString( "" ) , false ) ;
        CThostFtdcTraderApi ftdcTraderApi = PointerThostFtdcTraderApi.get( ) ;

        /**
         * 如果不加入这段代码，会导致 BridJ类中的public static synchronized Object getJavaObjectFromNativePeer(long peer) {
         * 获取不到strongNativeObjects的对应对象。
         */
        BridJ.protectFromGC( ftdcTraderApi ) ;        // 必须的

        CThostFtdcTraderSpi tradeSpi = new TestTradeSpi( ftdcTraderApi ) ;

        /**
         * 如果不加入这段代码，会导致 BridJ类中的public static synchronized Object getJavaObjectFromNativePeer(long peer) {
         * 获取不到strongNativeObjects的对应对象。
         */
        BridJ.protectFromGC( tradeSpi ) ;            // 必须的

        ftdcTraderApi.RegisterSpi( Pointer.pointerTo( tradeSpi ) ) ;

        ftdcTraderApi.RegisterFront( Pointer.pointerToCString( "tcp://10.253.117.107:13163" ) );
        ftdcTraderApi.Init();

        ftdcTraderApi.Join( ) ;

        System.out.println( "End TestTrade------------------------" ) ;
    }

}
