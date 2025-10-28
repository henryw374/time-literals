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
			
test-clj-temporal:
			clojure -Mtest-clj:test-clj-temporal:temporal -d temporal/test 
test-cljs-temporal:
			clojure -Atemporal:test-cljs:test-cljs-temporal -X cljs/tests-ci-temporal :compile-mode :release
install-temporal:
			make clean && clj -T:build jar :artifact "temporal" && clj -T:build install :artifact "temporal" \
			&& mkdir -p tmp && cd tmp
deploy-temporal:
			clj -T:build deploy :artifact "temporal"			



# hooray for stackoverflow
.PHONY: list
list:
		@$(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs
