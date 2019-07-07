package java12.cryptowin.service.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java12.cryptowin.entity.CryptoMonitor;
import java12.cryptowin.entity.enumClasses.CryptCoinType;
import java12.cryptowin.entity.enumClasses.CryptoExchange;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoBridgeParser {
    // отсутствуют XLM, TRON(TRX), EOS, IOTA

    public List<CryptoMonitor> parse() throws IOException {

        List<CryptoMonitor> list = new ArrayList();
        String apiUrl = "https://api.crypto-bridge.org/api/v1/ticker";
        Gson gson = new Gson();
        String stringGson = Jsoup.connect(apiUrl).ignoreContentType(true).get().text();

        ArrayList objects = gson.fromJson(stringGson, ArrayList.class);

        for (int i = 0; i < objects.size(); i++) {
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) objects.get(i);

            Object name = linkedTreeMap.get("id");
            if (name.toString().contains("_USDT")) {
                String nameCrypto = name.toString().substring(0, name.toString().length() - 5);
                Object buyingRate = linkedTreeMap.get("bid"); //цена покупки
                Object sellingRate = linkedTreeMap.get("ask"); //цена продажи
                if (nameCrypto.equals(CryptCoinType.BITCOIN.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN, CryptoExchange.CRYPTO_BRIDGE,
                            Double.parseDouble(buyingRate.toString()), LocalDate.now(), Double.parseDouble(sellingRate.toString())));
                } else if (nameCrypto.equals(CryptCoinType.ETHEREUM.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.ETHEREUM, CryptoExchange.CRYPTO_BRIDGE,
                            Double.parseDouble(buyingRate.toString()), LocalDate.now(), Double.parseDouble(sellingRate.toString())));
                } else if (nameCrypto.equals(CryptCoinType.XRP.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.XRP, CryptoExchange.CRYPTO_BRIDGE,
                            Double.parseDouble(buyingRate.toString()), LocalDate.now(), Double.parseDouble(sellingRate.toString())));
                } else if (nameCrypto.equals(CryptCoinType.LITECOIN.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.LITECOIN, CryptoExchange.CRYPTO_BRIDGE,
                            Double.parseDouble(buyingRate.toString()), LocalDate.now(), Double.parseDouble(sellingRate.toString())));
                } else if (nameCrypto.equals(CryptCoinType.BITCOIN_CASH.name()) || nameCrypto.equals("BCH")) {
                    list.add(new CryptoMonitor(CryptCoinType.BITCOIN_CASH, CryptoExchange.CRYPTO_BRIDGE,
                            Double.parseDouble(buyingRate.toString()), LocalDate.now(), Double.parseDouble(sellingRate.toString())));
                } else if (nameCrypto.equals(CryptCoinType.DASH.name())) {
                    list.add(new CryptoMonitor(CryptCoinType.DASH, CryptoExchange.CRYPTO_BRIDGE,
                            Double.parseDouble(buyingRate.toString()), LocalDate.now(), Double.parseDouble(sellingRate.toString())));
                }
            }
        }
        return list;
    }
}
