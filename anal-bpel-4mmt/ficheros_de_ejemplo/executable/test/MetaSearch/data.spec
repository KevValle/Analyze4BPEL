/* AVAILABILITY OF MSN AND GOOGLE */

// 75% percent true, 25% percent false - otherwise, results are not very interesting
typedef string(values={"true", "true", "true", "false"}) Boolean;

// Booleanos que nos indicaran si aparecen el cuerpo de cada agente
Boolean googleAvailable;
Boolean msnAvailable;

// Booleano que nos indicara si MSN responde o solicita una respuesta
Boolean msnResponse;

/* ORIGINAL REQUEST FOR THE COMPOSITION */

// Some "hot queries" from October 2011 according to Google Trends
typedef string(
    values =
    {
	"republican debate","gop debate","presidential debate","phoenix jones",
	"avengers trailer","gilad shalit","brady quinn","detroit lions",
	"new hampshire debate","last man standing","christopher columbus",
	"joe the plumber","joojoo","matthew stafford","brian urlacher","chicago bears",
	"bet hip hop awards 2011","calvin johnson","nick fairley","herman cain",
	"home affordable refinance program","demarco murray","mcrib","blaine gabbert",
	"dina manzo","jacksonville jaguars","rumpelstiltskin","nflx","maurice jones drew",
	"once upon a time","harp","s.978","world series game 5","northern lights","ravens",
	"beanie wells","sharia law","tim tebow","go ask alice","rightnow","andy rooney",
	"occupy oakland","tommy john surgery","amzn","student loan forgiveness",
	"st louis weather","lego man","denver weather","denver news","ed lee","tony romo",
	"crystal cathedral","joe the plumber","chaz bono","mark ingram",
	"weather colorado springs","daylight savings time","alcohol poisoning"
    }
) Query;

// Valores vacios en "language" y "country" para dar la posibilidad de que no se indique el idioma

// ISO 639-2 language code
typedef string(
    values =
    {
	"aa","ab","af","ak","sq","am","ar","an","hy","as","av","ae","ay","az","ba","bm",
	"eu","be","bn","bh","bi","bs","br","bg","my","ca","ch","ce","zh","cu","cv","kw",
	"co","cr","cs","da","dv","nl","dz","en","eo","et","ee","fo","fj","fi","fr","fy",
	"ff","ka","de","gd","ga","gl","gv","el","gn","gu","ht","ha","he","hz","hi","ho",
	"hr","hu","ig","is","io","ii","iu","ie","ia","id","ik","it","jv","ja","kl","kn",
	"ks","kr","kk","km","ki","rw","ky","kv","kg","ko","kj","ku","lo","la","lv","li",
	"ln","lt","lb","lu","lg","mk","mh","ml","mi","mr","ms","mg","mt","mn","na","nv",
	"nr","nd","ng","ne","nn","nb","no","ny","oc","oj","or","om","os","pa","fa","pi",
	"pl","pt","ps","qu","rm","ro","rn","ru","sg","sa","si","sk","sl","se","sm","sn",
	"sd","so","st","es","sc","sr","ss","su","sw","sv","ty","ta","tt","te","tg","tl",
	"th","bo","ti","to","tn","ts","tk","tr","tw","ug","uk","ur","uz","ve","vi","vo",
	"cy","wa","wo","xh","yi","yo","za","zu",""
    }
) Language;

// ISO 3166 country code
typedef string(
    values = {
	"AX","AF","AL","DZ","AS","AD","AO","AI","AQ","AG","AR","AM","AW","AU","AT","AZ",
	"BS","BH","BD","BB","BY","BE","BZ","BJ","BM","BT","BO","BA","BW","BV","BR","IO",
	"BN","BG","BF","BI","KH","CM","CA","CV","KY","CF","TD","CL","CN","CX","CC","CO",
	"KM","CD","CG","CK","CR","CI","HR","CU","CY","CZ","DK","DJ","DM","DO","EC","EG",
	"SV","GQ","ER","EE","ET","FK","FO","FJ","FI","FR","GF","PF","TF","GA","GM","GE",
	"DE","GH","GI","GR","GL","GD","GP","GU","GT","GN","GW","GY","HT","HM","HN","HK",
	"HU","IS","IN","ID","IR","IQ","IE","IL","IT","JM","JP","JO","KZ","KE","KI","KP",
	"KR","KW","KG","LA","LV","LB","LS","LR","LY","LI","LT","LU","MO","MK","MG","MW",
	"MY","MV","ML","MT","MH","MQ","MR","MU","YT","MX","FM","MD","MC","MN","MS","MA",
	"MZ","MM","NA","NR","NP","NL","AN","NC","NZ","NI","NE","NG","NU","NF","MP","NO",
	"OM","PK","PW","PS","PA","PG","PY","PE","PH","PN","PL","PT","PR","QA","RE","RO",
	"RU","RW","SH","KN","LC","PM","VC","WS","SM","ST","SA","SN","CS","SC","SL","SG",
	"SK","SI","SB","SO","ZA","GS","ES","LK","SD","SR","SJ","SZ","SE","CH","SY","TW",
	"TJ","TZ","TH","TL","TG","TK","TO","TT","TN","TR","TM","TC","TV","UG","UA","AE",
	"GB","US","UM","UY","UZ","VU","VA","VE","VN","VG","VI","WF","EH","YE","ZM","ZW", ""
    }
) Country;

// Modificado el minimo de resultados maximos
typedef int(min=-10,max=1000) MaxResults;

typedef tuple(element={Query,Language,Country,MaxResults}) Request;
Request meta;

// Valor numeric de las condiciones

typedef int(min=0,max=30) conditionValue;
conditionValue numeric;


typedef string(pattern="[A-Z][a-z0-9-]{0,2}") ShortId;
typedef string(pattern="[a-zA-Z0-9,.: ]{1,40}") LongRandomText;


/* CONDITIONS */
typedef int(min=0,max=500) conditionsNumber;
typedef tuple(element={conditionsNumber,LongRandomText}) condition;
typedef list(min=0,max=30,element={condition}) listCondition;
listCondition conditions;



/* DELAYS */

typedef int(min=0,max=10) Delay;
Delay Client_Delay;
Delay Google_Delay;
Delay MSN_Delay;



/* GOOGLE */

// We need to limit the number of possible domains to increase the
// probability of generating duplicates between MSN and Google

typedef string(pattern="[a-z]{1,3}[.](com|org)") Domain;
typedef string(values = {"Google", "", "MSN"}) Description;

typedef tuple(element={Domain,ShortId,LongRandomText,Boolean, Description}) GoogleResult;
typedef list(min=0,max=30,element={GoogleResult}) GoogleResults;

GoogleResults googleValues;





/* MSN RESPONSE*/

typedef int(min=-10) Offset;

typedef string(pattern="[a-zA-Z0-9 ]{0,20}") AlphaString;

typedef string(values={"Web","News","Ads","InlineAnswers","PhoneBook","WordBreaker","Spelling"}) Source;

typedef string(pattern="(http://|https://)[.][a-z]{1,3}") msnDomain;
typedef list(min=0,max=30,element={LongRandomText}) msnDescription;

typedef tuple(element={Domain,msnDescription,msnDomain,Boolean}) MsnResult;

typedef tuple(element={Source,Offset,MsnResult}) MsnResults;

MsnResults msnResponseValues;



/* MSN REQUEST */

typedef int(min=-4) AppId;
typedef int(min=1) query;
typedef string(values={"On","Off"}) safe;
typedef string(values={"Url","Title"}) field;

typedef tuple(element={LongRandomText,conditionsNumber}) searchTag;
typedef list(min=1,max=10,element={searchTag}) searchTags;

typedef tuple(element={Source,query,query,field}) request;
typedef list(min=1,max=10,element={request}) requests;

typedef tuple(element={Source,AppId,query,safe,searchTags,requests}) MsnRequest;

MsnRequest msnRequestValues;
