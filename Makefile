clean:
			clj -T:build clean
test-clj-jsjoda:
			clojure -Mtest-clj:test-clj-jsjoda:jsjoda -d jsjoda/test 
test-cljs-jsjoda:
			clojure -Ajsjoda:test-cljs:test-cljs-jsjoda -X com.widdindustries.tiado-cljs2/tests-ci-shadow :compile-mode :release
install-jsjoda:
			make clean && clj -T:build jar :artifact "jsjoda" && clj -T:build install :artifact "jsjoda" \
			&& mkdir -p tmp && cd tmp
deploy-jsjoda:
			clj -T:build deploy :artifact "jsjoda"
			
test-clj-tempo:
			clojure -Mtest-clj:test-clj-tempo:tempo -d tempo/test 
test-cljs-tempo:
			clojure -Atempo:test-cljs:test-cljs-tempo -X cljs/tests-ci-temporal :compile-mode :release
install-tempo:
			make clean && clj -T:build jar :artifact "tempo" && clj -T:build install :artifact "tempo" \
			&& mkdir -p tmp && cd tmp
deploy-tempo:
			clj -T:build deploy :artifact "tempo"			



# hooray for stackoverflow
.PHONY: list
list:
		@$(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs
