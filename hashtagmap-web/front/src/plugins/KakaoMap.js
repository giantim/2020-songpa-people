import { KAKAO_WEB_KEY } from "@/secret";
import navigatorUtils from "@/libs/navigator/navigator.js";
import dotImgSrc from "@/assets/dot.png";
import { KAKAO_MAP } from "@/utils/constants.js";
import { Notyf } from "notyf";
import "notyf/notyf.min.css";

/**
 * main.js 에 Vue.use(KaKaoMap) 을 해야 한다.
 * 사용할 컴포넌트에서 <div id="kakao-map"></div> 작성후
 * mounted 에 this.$loadMap() 호출을 하면 카카오 지도를 띄운다.
 *
 * kakao-map을 로드후에 map을 이용한 로직이 필요하다면 mounted 에 async 를 하고 await로 $loadMap()의 실행을 기다려야 한다.
 *
 * 맵의 초기 위치를 지정하고 싶다면 {latitude:위도값, longitute:경도값} 를 매개변수로 주입하면 된다.
 * 현재 좌표를 받아오지 못 한다면 기본 좌표는 잠실역 8번 출구이다.
 */
export default {
  install(Vue) {
    const script = document.createElement("script");
    script.src = KAKAO_MAP.API_SRC_ADDRESS + KAKAO_WEB_KEY;
    document.head.appendChild(script);
    const notyf = new Notyf();

    /* global kakao */
    const loadApi = new Promise(resolve => {
      script.onload = () => kakao.maps.load(resolve);
    });

    Vue.prototype.$initKakaoMapApi = async () => {
      await loadApi;
      return kakao.maps;
    };

    Vue.prototype.$loadMap = nowPosition => {
      const options = createOptions(nowPosition);
      const container = document.getElementById("kakao-map");

      this.map = new kakao.maps.Map(container, options);
      this.map.setMapTypeId(kakao.maps.MapTypeId.NORMAL);

      return this.map;
    };

    const createOptions = nowPosition => {
      return {
        center: createKakaoMapsLatLng(nowPosition),
        level: 5,
      };
    };

    Vue.prototype.$loadCurrentPosition = () => {
      const currentGeolocation = navigatorUtils.getCurrentPosition();

      currentGeolocation
        .then(position => {
          const currentPosition = navigatorUtils.extractGeolocationPosition(
            position,
          );
          setPositionCenter.call(this, currentPosition);
          displayUserMarker.call(this, currentPosition);
          notyf.success("사용자 위치를 불러왔습니다.");
        })
        .catch(() => {
          notyf.error("현재 위치를 불러오지 못했습니다.");
        });
    };

    const setPositionCenter = position => {
      this.map.setCenter(createKakaoMapsLatLng(position));
    };

    const createKakaoMapsLatLng = nowPosition => {
      const position = nowPosition || KAKAO_MAP.JAMSIL_STATION_8_EXIT;
      return new kakao.maps.LatLng(position.latitude, position.longitude);
    };

    const displayUserMarker = position => {
      setPositionCenter.call(this, position);

      const imageSize = new kakao.maps.Size(
        KAKAO_MAP.USER_MAKER_SIZE,
        KAKAO_MAP.USER_MAKER_SIZE,
      );
      const markerImage = new kakao.maps.MarkerImage(dotImgSrc, imageSize);
      const marker = new kakao.maps.Marker({
        position: createKakaoMapsLatLng(position),
        image: markerImage,
      });

      marker.setMap(this.map);
    };
  },
};
