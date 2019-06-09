package com.betomorrow.xamarin.tools.nunit

import com.betomorrow.xamarin.commands.CommandRunner
import spock.lang.Specification

import java.nio.file.Paths

class DefaultNunitConsoleTest extends Specification {

    CommandRunner runner
    NUnitConsole3Downloader downloader

    def "setup"() {
        runner = Mock()
        downloader = Mock()
    }

    def "run should call system command"() {
        given:
        def nunitConsole = new DefaultNunitConsole()
        nunitConsole.runner = runner
        nunitConsole.downloader = downloader
        def cmd

        when:
        nunitConsole.run(['a.dll', 'b.dll'], 'nunit2', null)

        then:
        1 * downloader.download(_) >> "sample/path"
        1 * runner.run(_) >> { arg ->
            cmd = arg[0]
            return 1
        }
        assert cmd.build() == ['mono', Paths.get('sample/path/nunit3-console.exe').toString(),
                               'a.dll', 'b.dll', '--result:TestResult.xml;format=nunit2']

    }

    def "run should use transform when defined"() {
        given:
        def nunitConsole = new DefaultNunitConsole()
        nunitConsole.runner = runner
        nunitConsole.downloader = downloader
        def cmd

        when:
        nunitConsole.run(['a.dll', 'b.dll'], null, 'nunit-transform.xslt')

        then:
        1 * downloader.download(_) >> "sample/path"
        1 * runner.run(_) >> { arg ->
            cmd = arg[0]
            return 1
        }
        assert cmd.build() == ['mono', Paths.get('sample/path/nunit3-console.exe').toString(),
                               'a.dll', 'b.dll', '--result:TestResult-Transformed.xml;transform=nunit-transform.xslt']
    }

    def "run should call system command with several result files when format and transform defined"() {
        given:
        def nunitConsole = new DefaultNunitConsole()
        nunitConsole.runner = runner
        nunitConsole.downloader = downloader
        def cmd

        when:
        nunitConsole.run(['a.dll', 'b.dll'], 'nunit2', 'nunit-transform.xslt')

        then:
        1 * downloader.download(_) >> "sample/path"
        1 * runner.run(_) >> { arg ->
            cmd = arg[0]
            return 1
        }
        assert cmd.build() == ['mono', Paths.get('sample/path/nunit3-console.exe').toString(),
                               'a.dll', 'b.dll',
                               '--result:TestResult.xml;format=nunit2',
                               '--result:TestResult-Transformed.xml;transform=nunit-transform.xslt']

    }
}
