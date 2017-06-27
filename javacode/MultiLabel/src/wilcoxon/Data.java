/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wilcoxon;

/**
 *
 * @author katie
 */
public class Data {
    
    //oneError: kleiner is beter
public static double[] oneErrorFlat = {0.3984095427435388, 0.694, 0.3469387755102041, 0.2524752475247525, 0.22452504317789293, 0.16029115688400186,  0.19069767441860466, 0.23662207357859533, 0.23555070883315157};

public static double[] oneErrorHier = {0.3920477137176938, 0.682, 0.3516483516483517, 0.26732673267326734, 0.229706390328152, 0.15928449744463374, 0.18294573643410852, 0.2265886287625418, 0.23882224645583425};

//coverage: kleiner is beter
public static double[] coverageFlat = {17.184095427435388, 57.208, 312.3579277864992, 1.816831683168317, 11.03972366148532, 14.229595787517423, 1.1054263565891473, 0.5250836120401338, 6.212649945474373};

public static double[] coverateHier = {17.060437375745526, 60.88, 313.36012558869703,1.8366336633663367, 11.065630397236616,14.224562490320583, 1.1472868217054264, 0.4882943143812709, 6.173391494002181}; 

//rankingLoss: kleiner is beter
public static double[] rankingLossFlat = {0.14639888042652874, 0.2427646033316897,0.16085811454834406, 0.15697194719471938, 0.10092075828954612, 0.062402272241575,0.06620971620971627, 0.08639214046822775, 0.1704907006842667 }; 

public static double[] rankingLossHier = {0.12291044512697959, 0.23837713706162425,  0.15974923574521305, 0.15715071507150705, 0.09684391300066832, 0.060844684155517255, 0.05783747230475195, 0.0803720735785956, 0.16792921878799788};


//average precision: hoe groter hoe beter
public static double[] averagePrecisionFlat = {0.5642439528475764, 0.3001485055946705, 0.400259677102365, 0.8183168316831683, 0.6999224189455312, 0.7511267343190907, 0.8720253476067427, 0.8566332218506144, 0.9107465907051123};

public static double[] averagePrecisionHier = {0.5719983122030567, 0.31263463567103283, 0.4255709500657339, 0.8128850385038501, 0.6980404980911893, 0.7494898764860429, 0.8776055753458732, 0.8638656633221864, 0.9246426066620022};


public static double[] microPRHier ={0.4766870299169792,0.33881494587565003,0.6058206487364396,0.8091288774415485,0.19258375100688016,0.7541789945017907,0.6542966957103392,0.835147498848117,0.7158365450181751} ;

public static double[] microROCHier = {0.9147868569900686, 0.8798019965485383,0.9096902019737376,0.9632818423383526,0.8287493143935405,0.8692857605267781,0.9412669431534751,0.9451236229279653,0.8413431567943006};

public static double[] macroPRHier = {0.3481844161186686,0.16905335298010443,0.23193010062633887,0.4209737085390849,0.09364138639716581,0.7530713805900788,0.23809349829681412,0.8288727832199948,0.4955246501988035};

public static double[] macroROCHier = {0.8866434850625687, 0.7348426999137352,0.7116316303297817,0.8320430024216297,0.6662425510121447,0.8534006121602712,0.7441202615173546,0.9392944589114873,0.6837796332624132};

public static double[] microPRFlat = {0.46312715596468984,0.33828886090334515,0.6036487991953546,0.8009394883709859,0.17817445929432132,0.7587938460685241,0.6545419454621623,0.8239710380205193,0.714348891362445};

public static double[] microROCFlat= {0.900630380949296,0.8792409878804185,0.9090772735672068,0.9578032329495129,0.8281095257895776,0.8612983874199645,0.9408537140430124,0.9382912314717555,0.8404217052220886};

public static double[] macroPRFlat= {0.33556211930327207,0.1690502647457518,0.2321476392666769,0.40870349841014847,0.08999985199919493,0.7472933294873335,0.23679062868651807,0.8190338177397773,0.49820210742829263};

public static double[] macroROCFlat= {0.8701484556657343,0.7341395515364192,0.711614141064246,0.8298249410971729,0.6612913922474674,0.8447423527936634,0.7485169355166972,0.9340697328715061,0.6948300142442588};










    
}
