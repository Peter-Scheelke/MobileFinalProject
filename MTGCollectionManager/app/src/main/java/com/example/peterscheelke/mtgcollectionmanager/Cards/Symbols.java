package com.example.peterscheelke.mtgcollectionmanager.Cards;

import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.HashMap;
import java.util.Map;

public class Symbols {

    private static Symbols uniqueInstance = null;

    private Map<String, Integer> symbols;

    private Symbols()
    {
        symbols = new HashMap<>();
    }

    public static int getIdFromSymbol(String symbol)
    {
        if (uniqueInstance == null)
        {
            uniqueInstance = new Symbols();
            uniqueInstance.SetupMap();
        }

        if (uniqueInstance.symbols.containsKey(symbol))
        {
            return uniqueInstance.symbols.get(symbol);
        }
        else
        {
            return 0;
        }
    }

    private void SetupMap()
    {
        // Basic Colors
        this.symbols.put("{W}", R.drawable.whitemana);
        this.symbols.put("{U}", R.drawable.bluemana);
        this.symbols.put("{B}", R.drawable.blackmana);
        this.symbols.put("{R}", R.drawable.redmana);
        this.symbols.put("{G}", R.drawable.greenmana);

        // Colorless
        this.symbols.put("{C}", R.drawable.colorlessmana);

        // Snow
        this.symbols.put("{S}", R.drawable.snowmana);

        // Phyrexian
        this.symbols.put("{W/P}", R.drawable.whitephyrexianmana);
        this.symbols.put("{U/P}", R.drawable.bluephyrexianmana);
        this.symbols.put("{B/P}", R.drawable.blackphyrexianmana);
        this.symbols.put("{R/P}", R.drawable.redphyrexianmana);
        this.symbols.put("{G/P}", R.drawable.greenphyrexianmana);

        // Hybrid
        this.symbols.put("{W/U}", R.drawable.wumana);
        this.symbols.put("{U/B}", R.drawable.ubmana);
        this.symbols.put("{B/R}", R.drawable.brmana);
        this.symbols.put("{R/G}", R.drawable.rgmana);
        this.symbols.put("{G/W}", R.drawable.gwmana);
        this.symbols.put("{W/B}", R.drawable.wbmana);
        this.symbols.put("{U/R}", R.drawable.urmana);
        this.symbols.put("{B/G}", R.drawable.bgmana);
        this.symbols.put("{R/W}", R.drawable.rwmana);
        this.symbols.put("{G/U}", R.drawable.gumana);
        this.symbols.put("{2/W}", R.drawable.w2mana);
        this.symbols.put("{2/U}", R.drawable.u2mana);
        this.symbols.put("{2/B}", R.drawable.b2mana);
        this.symbols.put("{2/R}", R.drawable.r2mana);
        this.symbols.put("{2/G}", R.drawable.g2mana);

        // X, Y, and Z
        this.symbols.put("{X}", R.drawable.xmana);
        this.symbols.put("{Y}", R.drawable.ymana);
        this.symbols.put("{Z}", R.drawable.zmana);

        // Generic
        this.symbols.put("{0}", R.drawable.zeromana);
        this.symbols.put("{1}", R.drawable.onemana);
        this.symbols.put("{2}", R.drawable.twomana);
        this.symbols.put("{3}", R.drawable.threemana);
        this.symbols.put("{4}", R.drawable.fourmana);
        this.symbols.put("{5}", R.drawable.fivemana);
        this.symbols.put("{6}", R.drawable.sixmana);
        this.symbols.put("{7}", R.drawable.sevenmana);
        this.symbols.put("{8}", R.drawable.eightmana);
        this.symbols.put("{9}", R.drawable.ninemana);
        this.symbols.put("{10}", R.drawable.tenmana);
        this.symbols.put("{11}", R.drawable.elevenmana);
        this.symbols.put("{12}", R.drawable.twelvemana);
        this.symbols.put("{13}", R.drawable.thirteenmana);
        this.symbols.put("{14}", R.drawable.fourteenmana);
        this.symbols.put("{15}", R.drawable.fifteenmana);
        this.symbols.put("{16}", R.drawable.sixteenmana);
        this.symbols.put("{20}", R.drawable.twentymana);

        // Misc. Mana Symbols
        this.symbols.put("{1000000}", R.drawable.gleemaxmana);
        this.symbols.put("{∞}", R.drawable.inifinitemana);
        this.symbols.put("{100}", R.drawable.hundredmana);

        // Other Symbols
        this.symbols.put("{T}", R.drawable.tapsymbol);
        this.symbols.put("{Q}", R.drawable.untapsymbol);
        this.symbols.put("{E}", R.drawable.energysymbol);
    }



}
